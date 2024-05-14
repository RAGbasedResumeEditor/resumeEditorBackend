package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ResumeManagementService {
    Page<ResumeBoard> getResumeBoards(int page);
    void deleteResume(ResumeBoardDTO rbDto);
    void updateResume(ResumeBoardDTO rbDto);
    List<ResumeBoard> searchByTitle(String title, int page, int size);
    List<ResumeBoard> searchByRating(Float rating, int page, int size);

    Boolean checkResumeExists(Long rNum);
}
