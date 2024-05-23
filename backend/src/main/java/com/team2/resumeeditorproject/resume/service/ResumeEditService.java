package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import com.team2.resumeeditorproject.resume.dto.ResumeEditDTO;
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

    ResumeEditDTO convertToDto(ResumeEdit resumeEdit);
    void setModelMapper(ModelMapper modelMapper);
}
