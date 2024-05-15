package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import org.springframework.data.domain.Page;

public interface ResumeManagementService {
    Page<ResumeBoard> getResumeBoards(int page);
    void deleteResume(Long rNum);
    //void updateResume(ResumeBoardDTO rbDto);
    Page<ResumeBoard> searchByTitle(String title, int page);
    Page<ResumeBoard> searchByRating(Float rating, int page);
    Boolean checkResumeExists(Long rNum);
}
