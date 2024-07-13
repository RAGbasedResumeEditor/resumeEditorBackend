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

    Object getResumeBoard(long resumeNo);

    Page<Object[]> searchBoard(String keyword, Pageable pageable);

    ResumeBoardDTO getResumeBoardForRating(Long resumeNo);

    int updateRatingCount(Long resumeNo, int newRatingCount, float newRating);

    List<Object[]> getBoardRankingReadNum();

    List<Object[]> getBoardRankingRating();

    Page<Object[]> getBookmarkList(long userNo, Pageable pageable);


//    float getRating(long resumeNo);
}
