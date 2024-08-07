package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.domain.Guide;
import com.team2.resumeeditorproject.resume.dto.BookmarkDTO;
import com.team2.resumeeditorproject.resume.dto.GuideDTO;

public interface GuideService {
    GuideDTO insertGuide(GuideDTO guideDTO);
    GuideDTO convertToDto(Guide guide);
}
