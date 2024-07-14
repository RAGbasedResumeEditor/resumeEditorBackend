package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.exception.NotFoundException;
import com.team2.resumeeditorproject.resume.domain.ResumeGuide;
import com.team2.resumeeditorproject.resume.dto.ResumeGuideDTO;
import com.team2.resumeeditorproject.resume.repository.ResumeGuideRepository;
import com.team2.resumeeditorproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeGuideServiceImpl implements ResumeGuideService{

    private final ResumeGuideRepository resumeGuideRepository;

    private final ModelMapper modelMapper;

    private final UserService userService;

    @Override
    public ResumeGuideDTO saveResumeGuide(ResumeGuideDTO resumeGuideDTO) {
        ResumeGuide resumeGuide = modelMapper.map(resumeGuideDTO, ResumeGuide.class);
        ResumeGuide savedResumeGuide = resumeGuideRepository.save(resumeGuide);
        return modelMapper.map(savedResumeGuide, ResumeGuideDTO.class);
    }

    @Override
    public Page<ResumeGuide> getResumeGuidesByUNum(Long uNum, Pageable pageable) {
        return resumeGuideRepository.findByUNum(uNum, pageable);
    }

    @Override
    public ResumeGuideDTO getResumeGuideDetail(Long gNum, String username) {
        ResumeGuide resumeGuide = getResumeGuideByGNum(gNum);
        if (resumeGuide == null) {
            throw new NotFoundException("Resume guide with gNum " + gNum + " not found");
        }
        Long uNum = userService.showUser(userService.getUsername()).getUNum();
        if (!uNum.equals(resumeGuide.getUNum())) {
            throw new BadRequestException(" - 잘못된 접근입니다. (로그인한 사용자의 가이드 기록이 아닙니다)");
        }

        return ResumeGuideDTO.builder()
                .gNum(resumeGuide.getGNum())
                .uNum(resumeGuide.getUNum())
                .company(resumeGuide.getCompany())
                .occupation(resumeGuide.getOccupation())
                .content(resumeGuide.getContent())
                .build();
    }

    @Override
    public ResumeGuide getResumeGuideByGNum(Long gNum) {
        return resumeGuideRepository.findById(gNum).orElse(null);
    }
}
