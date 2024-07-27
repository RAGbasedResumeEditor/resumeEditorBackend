package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.common.enumeration.ResultMessage;
import com.team2.resumeeditorproject.resume.dto.ResumeEditDTO;
import com.team2.resumeeditorproject.user.dto.UserDTO;

/**
 * resumeEditService
 *
 * @author : 안은비
 * @fileName : ResumeEditService
 * @since : 04/25/24
 */
public interface ResumeEditService {
    ResultMessage saveResumeEdit(ResumeEditDTO resumeEditDTO, UserDTO user);
}
