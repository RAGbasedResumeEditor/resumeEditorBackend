package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.domain.ResumeGuide;
import com.team2.resumeeditorproject.resume.dto.ResumeGuideDTO;
import com.team2.resumeeditorproject.resume.repository.ResumeGuideRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ResumeGuideServiceImpl implements ResumeGuideService{

    @Autowired
    private ResumeGuideRepository resumeGuideRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResumeGuideDTO saveResumeGuide(ResumeGuideDTO resumeGuideDTO) {
        ResumeGuide resumeGuide = modelMapper.map(resumeGuideDTO, ResumeGuide.class);
        ResumeGuide savedResumeGuide = resumeGuideRepository.save(resumeGuide);
        return modelMapper.map(savedResumeGuide, ResumeGuideDTO.class);
    }

    @Override
    public Page<ResumeGuide> getResumeGuidesByUserNo(Long userNo, Pageable pageable) {
        return resumeGuideRepository.findByUserUserNo(userNo, pageable);
    }

    @Override
    public ResumeGuide getResumeGuideByResumeGuideNo(Long resumeGuideNo) {
        return resumeGuideRepository.findById(resumeGuideNo).orElse(null);
    }
}
