package com.team2.resumeeditorproject.resume.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team2.resumeeditorproject.common.enumeration.ResultMessage;
import com.team2.resumeeditorproject.company.repository.CompanyRepository;
import com.team2.resumeeditorproject.occupation.repository.OccupationRepository;
import com.team2.resumeeditorproject.resume.domain.Resume;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import com.team2.resumeeditorproject.resume.dto.ResumeEditDTO;
import com.team2.resumeeditorproject.resume.repository.ResumeBoardRepository;
import com.team2.resumeeditorproject.resume.repository.ResumeEditRepository;
import com.team2.resumeeditorproject.resume.repository.ResumeRepository;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import com.team2.resumeeditorproject.user.service.UserService;

import lombok.RequiredArgsConstructor;

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
	private final ResumeBoardRepository resumeBoardRepository;
	private final UserService userService;
	private final ResumeRepository resumeRepository;
	private final UserRepository userRepository;
	private final CompanyRepository companyRepository;
	private final OccupationRepository occupationRepository;

	@Transactional
	@Override
	public ResultMessage saveResumeEdit(ResumeEditDTO resumeEditDTO, UserDTO loginUser) {

		User user = userRepository.findById(loginUser.getUserNo()).orElseThrow(() -> new IllegalArgumentException("Invalid User"));

		ResumeEdit resumeEdit = ResumeEdit.builder()
				.options(resumeEditDTO.getOptions())
				.content(resumeEditDTO.getContent())
				.mode(resumeEditDTO.getMode())
				.resume(resumeRepository.findById(resumeEditDTO.getResumeNo()).orElseThrow(() -> new IllegalArgumentException("Invalid Resume")))
				.user(user)
				.company(companyRepository.findById(resumeEditDTO.getCompanyNo()).orElseThrow(() -> new IllegalArgumentException("Invalid Company No: " + resumeEditDTO.getCompanyNo())))
				.occupation(occupationRepository.findById(resumeEditDTO.getOccupationNo()).orElseThrow(() -> new IllegalArgumentException("Invalid Occupation No: " + resumeEditDTO.getOccupationNo())))
				.build();

		resumeEditRepository.save(resumeEdit);

		Resume resume = Resume.builder()
				.content(resumeEditDTO.getContent())
				.resumeEdit(resumeEdit)
				.user(user)
				.build();

		resumeRepository.save(resume);

		// mode가 pro(2)인 경우, resume_board 테이블에 저장하고 user mode 2로 변경
		if (resumeEditDTO.getMode() == 2) {
			ResumeBoard resumeBoard = ResumeBoard.builder()
					.title(resumeEditDTO.getCompanyName() + " " + resumeEditDTO.getOccupationName())
					.resume(resume)
					.build();

			resumeBoardRepository.save(resumeBoard);
			userService.updateUserMode(2);
		}

		return ResultMessage.Registered;
	}
}
