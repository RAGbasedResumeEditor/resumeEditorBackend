package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.dto.ResumeDTO;
import com.team2.resumeeditorproject.user.dto.ResumeEditDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * resumeService
 *
 * @author : 안은비
 * @fileName : ResumeService
 * @since : 04/26/24
 */
public interface ResumeService {
    ResumeDTO insertResume(ResumeDTO resumeDTO);

    String getResumeContent(long resumeNo);

    ResumeEditDetailDTO getResumeEditDetail(Long num, String username);

    Page<Object[]> myPageEditList(long userNo, Pageable pageable);

}
