package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    Page<Object[]> getAllResumeBoards(Pageable pageable);

    Object getResumeBoard(long r_num);

    Page<Object[]> searchBoard(String keyword, Pageable pageable);

    ResumeBoardDTO getResumeBoardForRating(Long r_num);

    int updateRatingCount(Long rNum, int newRatingCount, float newRating);

    List<Object[]> getBoardRankingReadNum();

    List<Object[]> getBoardRankingRating();

//    float getRating(long r_num);
}
