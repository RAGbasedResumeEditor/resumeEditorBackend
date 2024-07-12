package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResumeManagementService {
    Page<ResumeBoardDTO> getPagedResumeBoards(Pageable pageable);
    Page<ResumeBoardDTO> searchPagedResumeBoardByTitle(String title, int pageNo);
    Page<ResumeBoardDTO> searchPagedResumeBoardByRating(Float rating, int pageNo);
    void deleteResume(Long rNum);
    Boolean checkResumeExists(Long rNum);
}
