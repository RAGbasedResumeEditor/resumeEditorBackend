package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;

import java.util.List;

/**
 * resumeBoardService
 *
 * @author : 안은비
 * @fileName : ResumeBoardService
 * @since : 04/30/24
 */
public interface ResumeBoardService {
    ResumeBoardDTO insertResumeBoard(ResumeBoardDTO resumeboardDTO);

    List<Object[]> getAllResumeBoards();

    Object getResumeBoard(long r_num);
}
