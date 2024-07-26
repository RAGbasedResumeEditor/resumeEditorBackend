package com.team2.resumeeditorproject.resume.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.team2.resumeeditorproject.common.enumeration.ResultMessage;
import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.exception.NotFoundException;
import com.team2.resumeeditorproject.resume.domain.ResumeGuide;
import com.team2.resumeeditorproject.resume.dto.ResumeGuideDTO;
import com.team2.resumeeditorproject.company.repository.CompanyRepository;
import com.team2.resumeeditorproject.occupation.repository.OccupationRepository;
import com.team2.resumeeditorproject.resume.repository.ResumeGuideRepository;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import com.team2.resumeeditorproject.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumeGuideServiceImpl implements ResumeGuideService {

	private final ResumeGuideRepository resumeGuideRepository;
	private final OccupationRepository occupationRepository;
	private final CompanyRepository companyRepository;
	private final UserRepository userRepository;
	private final UserService userService;

	@Override
	public ResultMessage saveResumeGuide(ResumeGuideDTO resumeGuideDTO, UserDTO loginUser) {
		ResumeGuide resumeGuide = ResumeGuide.builder()
				.content(resumeGuideDTO.getContent())
				.occupation(occupationRepository.findById(resumeGuideDTO.getOccupationNo()).orElseThrow(() -> new IllegalArgumentException("Invalid Occupation No: " + resumeGuideDTO.getOccupationNo())))
				.company(companyRepository.findById(resumeGuideDTO.getCompanyNo()).orElseThrow(() -> new IllegalArgumentException("Invalid Company No: " + resumeGuideDTO.getCompanyNo())))
				.user(userRepository.findById(loginUser.getUserNo()).orElseThrow(() -> new IllegalArgumentException("Invalid user ID")))
				.build();

		resumeGuideRepository.save(resumeGuide);

		return ResultMessage.Registered;
	}

	@Override
	public Page<ResumeGuide> getResumeGuidesByUserNo(Long userNo, Pageable pageable) {
		return resumeGuideRepository.findByUserUserNo(userNo, pageable);
	}

	@Override
	public ResumeGuideDTO getResumeGuideDetail(Long gNum, String username) {
		ResumeGuide resumeGuide = resumeGuideRepository.findById(gNum).orElse(null);
		if (resumeGuide == null) {
			throw new NotFoundException("Resume guide with gNum " + gNum + " not found");
		}
		Long userNo = userService.showUser(username).getUserNo();
		if (!userNo.equals(resumeGuide.getUser().getUserNo())) {
			throw new BadRequestException(" - 잘못된 접근입니다. (로그인한 사용자의 가이드 기록이 아닙니다)");
		}

		return ResumeGuideDTO.builder()
				.resumeGuideNo(resumeGuide.getResumeGuideNo())
				.userNo(resumeGuide.getUser().getUserNo())
				.companyNo(resumeGuide.getCompany().getCompanyNo())
				.companyName(resumeGuide.getCompany().getCompanyName())
				.occupationNo(resumeGuide.getOccupation().getOccupationNo())
				.occupationName(resumeGuide.getOccupation().getOccupationName())
				.content(resumeGuide.getContent())
				.build();
	}
}
