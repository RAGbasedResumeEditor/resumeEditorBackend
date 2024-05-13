package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;

import java.util.List;

public interface ResumeManagementService {
    List<ResumeBoard> getAllResume();
    void deleteResume(ResumeBoardDTO rbDto);
    void updateResume(ResumeBoardDTO rbDto);
}
