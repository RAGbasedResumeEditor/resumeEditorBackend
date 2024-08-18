package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.user.dto.ResumeEditDetailDTO;

/**
 * resumeService
 *
 * @author : 안은비
 * @fileName : ResumeService
 * @since : 04/26/24
 */
public interface ResumeService {
	ResumeEditDetailDTO getResumeEditDetail(Long num, String username);
}
