package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.common.enumeration.ResultMessage;
import com.team2.resumeeditorproject.resume.domain.ResumeGuide;
import com.team2.resumeeditorproject.resume.dto.ResumeGuideDTO;
import com.team2.resumeeditorproject.user.dto.UserDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ResumeGuideService {
    ResultMessage saveResumeGuide(ResumeGuideDTO resumeGuideDTO, UserDTO loginUser);

    Page<ResumeGuide> getResumeGuidesByUserNo(Long userNo, Pageable pageable);

    ResumeGuideDTO getResumeGuideDetail(Long resumeGuideNo, String username);
}
