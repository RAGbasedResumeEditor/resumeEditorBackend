package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.domain.ResumeGuide;
import com.team2.resumeeditorproject.resume.dto.ResumeGuideDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ResumeGuideService {
    ResumeGuideDTO saveResumeGuide(ResumeGuideDTO resumeGuideDTO);
    Page<ResumeGuide> getResumeGuidesByUserNo(Long userNo, Pageable pageable);

    ResumeGuideDTO getResumeGuideDetail(Long resumeGuideNo, String username);
}
