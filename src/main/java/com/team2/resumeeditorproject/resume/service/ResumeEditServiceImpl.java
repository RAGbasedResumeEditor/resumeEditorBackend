package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.common.enumeration.ResultMessage;
import com.team2.resumeeditorproject.common.util.PageUtil;
import com.team2.resumeeditorproject.company.repository.CompanyRepository;
import com.team2.resumeeditorproject.occupation.repository.OccupationRepository;
import com.team2.resumeeditorproject.resume.domain.Resume;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import com.team2.resumeeditorproject.resume.domain.ResumeEditExtension;
import com.team2.resumeeditorproject.resume.dto.ResumeEditDTO;
import com.team2.resumeeditorproject.resume.enumulation.ResumeEditExtensionType;
import com.team2.resumeeditorproject.resume.dto.request.ResumeEditRequest;
import com.team2.resumeeditorproject.resume.repository.ResumeBoardRepository;
import com.team2.resumeeditorproject.resume.repository.ResumeEditRepository;
import com.team2.resumeeditorproject.resume.repository.ResumeRepository;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import com.team2.resumeeditorproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * resumeEditServiceImpl
 *
 * @author : 안은비
 * @fileName : ResumeEditServiceImpl
 * @since : 04/25/24
 */
@Service
@RequiredArgsConstructor
public class ResumeEditServiceImpl implements ResumeEditService {
	private final ResumeEditRepository resumeEditRepository;
	private final ResumeRepository resumeRepository;
	private final ResumeBoardRepository resumeBoardRepository;
	private final UserService userService;
	private final UserRepository userRepository;
	private final CompanyRepository companyRepository;
	private final OccupationRepository occupationRepository;
	private final ResumeEditExtensionService resumeEditExtensionService;

	@Transactional
	@Override
	public ResultMessage saveResumeEdit(ResumeEditRequest resumeEditRequest, UserDTO loginUser) {

		User user = userRepository.findById(loginUser.getUserNo()).orElseThrow(() -> new IllegalArgumentException("Invalid User"));

		Resume resume = Resume.builder()
				.content(resumeEditRequest.getBeforeContent())
				.user(user)
				.build();

		resumeRepository.save(resume);

		ResumeEdit resumeEdit = ResumeEdit.builder()
				.options(resumeEditRequest.getOptions())
				.content(resumeEditRequest.getAfterContent())
				.mode(resumeEditRequest.getMode())
				.resume(resume)
				.user(user)
				.company(companyRepository.findById(resumeEditRequest.getCompanyNo()).orElse(null))
				.occupation(occupationRepository.findById(resumeEditRequest.getOccupationNo()).orElse(null))
				.question(resumeEditRequest.getQuestion())
				.build();

		resumeEditRepository.save(resumeEdit);

		resumeEditRequest.setResumeEditNo(resumeEdit.getResumeEditNo());
		resumeEditExtensionService.saveResumeEditExtension(resumeEditRequest);

		// mode가 pro(2)인 경우, resume_board 테이블에 저장하고 user mode 2로 변경
		if (resumeEditRequest.getMode() == 2) {
			ResumeBoard resumeBoard = ResumeBoard.builder()
					.title(resumeEditRequest.getCompanyName() + " " + resumeEditRequest.getOccupationName())
					.resume(resume)
					.build();

			resumeBoardRepository.save(resumeBoard);
			userService.updateUserMode(2);
		}

		return ResultMessage.Registered;
	}

	@Override
	public Page<ResumeEditDTO> myPageEditList(long userNo, int pageNo, int size) {
		PageUtil.checkUnderZero(pageNo);

		Pageable pageable = PageRequest.of(pageNo, size);

		Page<ResumeEdit> resumeEdits = resumeEditRepository.findAllByUserUserNoOrderByResumeEditNoDesc(userNo, pageable);

		PageUtil.checkListEmpty(resumeEdits);
		PageUtil.checkExcessLastPageNo(pageable.getPageNumber(), resumeEdits.getTotalPages() - 1);

		return new PageImpl<>(resumeEdits.stream()
				.map(resumeEdit -> {
					String companyName = resumeEdit.getCompany() != null ? resumeEdit.getCompany().getCompanyName() : resumeEdit.getEntityExtensionList().stream()
						.filter(resumeEditExtension -> resumeEditExtension.getResumeEditExtensionType() == ResumeEditExtensionType.Company)
						.findFirst()
						.orElse(ResumeEditExtension.empty).getContent();

					String occupationName = resumeEdit.getOccupation() != null ? resumeEdit.getOccupation().getOccupationName() : resumeEdit.getEntityExtensionList().stream()
						.filter(resumeEditExtension -> resumeEditExtension.getResumeEditExtensionType() == ResumeEditExtensionType.Occupation)
						.findFirst()
						.orElse(ResumeEditExtension.empty).getContent();

					return ResumeEditDTO.builder()
						.resumeEditNo(resumeEdit.getResumeEditNo())
						.mode(resumeEdit.getMode())
						.companyName(companyName)
						.question(resumeEdit.getQuestion())
						.occupationName(occupationName)
						.createdDate(resumeEdit.getResume().getCreatedDate())
						.build();
				})
				.toList(), pageable, resumeEdits.getTotalElements());
	}
}
