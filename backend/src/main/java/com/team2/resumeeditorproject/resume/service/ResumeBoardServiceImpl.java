package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import com.team2.resumeeditorproject.resume.repository.ResumeBoardRepository;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * resumeBoardServiceImpl
 *
 * @author : 안은비
 * @fileName : ResumeBoardServiceImpl
 * @since : 04/30/24
 */
@Service
public class ResumeBoardServiceImpl implements ResumeBoardService{
    @Autowired
    private ResumeBoardRepository resumeBoardRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResumeBoardDTO insertResumeBoard(ResumeBoardDTO resumeboardDTO) {
        ResumeBoard resumeBoard = modelMapper.map(resumeboardDTO, ResumeBoard.class);
        ResumeBoard savedResumeBoard = resumeBoardRepository.save(resumeBoard);
        return modelMapper.map(savedResumeBoard, ResumeBoardDTO.class);
    }


    @Override
    public List<Object[]> getAllResumeBoards() {
        return resumeBoardRepository.findAllResumeBoards();
    }

    @Override
    public Object getResumeBoard(long r_num) {
        return resumeBoardRepository.findResumeBoard(r_num);
    }


}
