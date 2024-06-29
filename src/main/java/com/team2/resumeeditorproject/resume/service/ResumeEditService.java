package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import com.team2.resumeeditorproject.resume.dto.ResumeEditDTO;
import com.team2.resumeeditorproject.user.domain.User;
import org.modelmapper.ModelMapper;

import java.util.List;
/**
 * resumeEditService
 *
 * @author : 안은비
 * @fileName : ResumeEditService
 * @since : 04/25/24
 */
public interface ResumeEditService {
    ResumeEditDTO insertResumeEdit(ResumeEditDTO resumeEditDTO);
    ResumeEditDTO insertResumeEditTransaction(ResumeEditDTO resumeEditDTO, String content, User user, int mode) throws Exception;
    ResumeEditDTO convertToDto(ResumeEdit resumeEdit);
    void setModelMapper(ModelMapper modelMapper);
}
