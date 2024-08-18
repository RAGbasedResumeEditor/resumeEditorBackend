package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import com.team2.resumeeditorproject.resume.dto.request.ResumeBoardRequest;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * resumeBoardService
 *
 * @author : 안은비
 * @fileName : ResumeBoardService
 * @since : 04/30/24
 */
public interface ResumeBoardService {
    ResumeBoardDTO getResumeBoard(long resumeBoardNo);

    Page<ResumeBoardDTO> getPagedResumeBoardsContainingTitle(ResumeBoardRequest resumeBoardRequest);

    List<ResumeBoardDTO> getHighestReadCountResumeBoard();

    List<ResumeBoardDTO> getHighestRatingResumeBoard();

    boolean isNotExistResumeBoard(long resumeBoardNo);
}
