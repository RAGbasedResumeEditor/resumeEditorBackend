package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.dto.ResumeEditDTO;
import com.team2.resumeeditorproject.resume.repository.ResumeEditRepository;
import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeEditServiceImpl implements ResumeEditService{
    @Autowired
    private ResumeEditRepository resumeEditRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ResumeEditDTO insertResume(ResumeEditDTO resumeEditDTO) {
        ResumeEdit resumeEdit = modelMapper.map(resumeEditDTO, ResumeEdit.class);
        ResumeEdit savedResume = resumeEditRepository.save(resumeEdit);
        return modelMapper.map(savedResume, ResumeEditDTO.class);
    }

    @Override
    public List<ResumeEditDTO> getAllResumeEdit() {
        return null;
    }
}
