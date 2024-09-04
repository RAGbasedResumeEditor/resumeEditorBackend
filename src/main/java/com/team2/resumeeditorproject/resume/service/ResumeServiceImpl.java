package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.exception.NotFoundException;
import com.team2.resumeeditorproject.resume.domain.Resume;
import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import com.team2.resumeeditorproject.resume.repository.ResumeEditRepository;
import com.team2.resumeeditorproject.resume.repository.ResumeRepository;
import com.team2.resumeeditorproject.user.dto.ResumeEditDetailDTO;
import com.team2.resumeeditorproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * resumeServiceImpl
 *
 * @author : 안은비
 * @fileName : ResumeServiceImpl
 * @since : 04/29/24
 */
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
	private final ResumeRepository resumeRepository;
	private final ResumeEditRepository resumeEditRepository;
	private final UserService userService;

	@Override
	public ResumeEditDetailDTO getResumeEditDetail(Long rNum, String username) {
		ResumeEdit resumeEdit = resumeEditRepository.findResumeEditsByResumeEditNo(rNum)
				.stream()
				.findFirst()
				.orElse(null);
		if (resumeEdit == null) {
			throw new NotFoundException(" - resume edit with r_num " + rNum + " not found");
		}

		Resume resume = resumeEdit.getResume();
		if (resume == null) { // 해당하는 첨삭 기록이 없다면
			throw new NotFoundException(" - resume with r_num " + rNum + " not found");
		}

		Long uNum = userService.showUser(username).getUserNo();
		if (!uNum.equals(resume.getUser().getUserNo())) {
			throw new BadRequestException(" - 잘못된 접근입니다. (로그인한 사용자의 첨삭 기록이 아닙니다)");
		}

		return ResumeEditDetailDTO.builder()
				.resumeEditNo(resumeEdit.getResumeEditNo())
				.companyNo(resumeEdit.getCompany().getCompanyNo())
				.companyName(resumeEdit.getCompany().getCompanyName())
				.occupationNo(resumeEdit.getOccupation().getOccupationNo())
				.occupationName(resumeEdit.getOccupation().getOccupationName())
				.questions(resumeEdit.getQuestion())
				.options(resumeEdit.getOptions())
				.beforeContent(resumeEdit.getContent())
				.mode(resumeEdit.getMode())
				.afterContent(resume.getContent())
				.createdDate(resume.getCreatedDate())
				.build();
	}

}
