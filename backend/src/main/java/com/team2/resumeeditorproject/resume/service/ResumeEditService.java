package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.dto.ResumeEditDTO;

import java.util.List;

public interface ResumeEditService {
    ResumeEditDTO insertResume(ResumeEditDTO resumeEditDTO);

    List<ResumeEditDTO> getAllResumeEdit();
}
