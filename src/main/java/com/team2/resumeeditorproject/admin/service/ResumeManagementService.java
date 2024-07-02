package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResumeManagementService {
    Page<ResumeBoard> getResumeBoards(int pageNo);
    Page<ResumeBoardDTO> getPagedResumeBoards(Pageable pageable);
    void deleteResume(Long rNum);
    Page<ResumeBoard> searchByTitle(String title, int pageNo);
    Page<ResumeBoard> searchByRating(Float rating, int pageNo);
    Boolean checkResumeExists(Long rNum);
}
