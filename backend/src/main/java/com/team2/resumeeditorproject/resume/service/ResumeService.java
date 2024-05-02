package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.dto.ResumeDTO;
/**
 * resumeService
 *
 * @author : 안은비
 * @fileName : ResumeService
 * @since : 04/26/24
 */
public interface ResumeService {
    ResumeDTO insertResume(ResumeDTO resumeDTO);
    String getResumeContent(long r_num);
}

