package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.common.enumeration.ResultMessage;
import com.team2.resumeeditorproject.resume.dto.ResumeGuideDTO;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import org.springframework.data.domain.Page;


public interface ResumeGuideService {
    ResultMessage saveResumeGuide(ResumeGuideDTO resumeGuideDTO, UserDTO loginUser);

    Page<ResumeGuideDTO> getResumeGuidesByUserNo(Long userNo, int pageNo, int size);

    ResumeGuideDTO getResumeGuideDetail(Long resumeGuideNo, String username);
}
