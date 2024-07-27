package com.team2.resumeeditorproject.resume.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.team2.resumeeditorproject.common.util.PageUtil;
import com.team2.resumeeditorproject.exception.DataNotFoundException;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import com.team2.resumeeditorproject.resume.dto.request.ResumeBoardRequest;
import com.team2.resumeeditorproject.resume.repository.ResumeBoardRepository;

/**
 * resumeBoardServiceImpl
 *
 * @author : 안은비
 * @fileName : ResumeBoardServiceImpl
 * @since : 04/30/24
 */
@Service
public class ResumeBoardServiceImpl implements ResumeBoardService {
	@Autowired
	private ResumeBoardRepository resumeBoardRepository;

	@Override
	public ResumeBoardDTO getResumeBoard(long resumeBoardNo) {
		ResumeBoard resumeBoard = resumeBoardRepository.findById(resumeBoardNo).orElseThrow(() -> new DataNotFoundException("게시물이 없습니다"));
		resumeBoardRepository.updateReadCount(resumeBoardNo);

		return ResumeBoardDTO.builder()
				.resumeBoardNo(resumeBoard.getResumeBoardNo())
				.rating((float)Math.round(resumeBoard.getRating() * 10) / 10)
				.ratingCount(resumeBoard.getRatingCount())
				.readCount(resumeBoard.getReadCount())
				.title(resumeBoard.getTitle())
				.content(resumeBoard.getResume().getContent())
				.createdDate(resumeBoard.getResume().getCreatedDate())
				.username(resumeBoard.getResume().getUser().getUsername())
				.questions(resumeBoard.getResume().getResumeEdit().getCompany().getQuestions())
				.build();
	}

	@Override
	public Page<ResumeBoardDTO> getPagedResumeBoardsContainingTitle(ResumeBoardRequest resumeBoardRequest) {
		PageUtil.checkUnderZero(resumeBoardRequest.getPageNo());

		Pageable pageable = PageRequest.of(resumeBoardRequest.getPageNo(), resumeBoardRequest.getPageSize());
		Page<ResumeBoard> resumeBoards = resumeBoardRepository.findAllByTitleContaining(StringUtils.defaultString(resumeBoardRequest.getKeyword()), pageable);

		PageUtil.checkListEmpty(resumeBoards);
		PageUtil.checkExcessLastPageNo(pageable.getPageNumber(), resumeBoards.getTotalPages() - 1);

		return new PageImpl<>(resumeBoards.stream()
				.map(resumeBoard -> ResumeBoardDTO.builder()
						.resumeBoardNo(resumeBoard.getResumeBoardNo())
						.rating((float)Math.round(resumeBoard.getRating() * 10) / 10)
						.ratingCount(resumeBoard.getRatingCount())
						.readCount(resumeBoard.getReadCount())
						.title(resumeBoard.getTitle())
						.content(resumeBoard.getResume().getContent())
						.createdDate(resumeBoard.getResume().getCreatedDate())
						.build())
				.toList(), pageable, resumeBoards.getTotalElements());
	}

	@Override
	public List<ResumeBoardDTO> getHighestReadCountResumeBoard() {
		List<ResumeBoard> resumeBoards = resumeBoardRepository.findTop3ByOrderByReadCountDesc();

		return resumeBoards.stream()
				.map(resumeBoard -> ResumeBoardDTO.builder()
						.resumeBoardNo(resumeBoard.getResumeBoardNo())
						.rating((float)Math.round(resumeBoard.getRating() * 10) / 10)
						.ratingCount(resumeBoard.getRatingCount())
						.readCount(resumeBoard.getReadCount())
						.title(resumeBoard.getTitle())
						.content(resumeBoard.getResume().getContent())
						.createdDate(resumeBoard.getResume().getCreatedDate())
						.build())
				.toList();
	}

	@Override
	public List<ResumeBoardDTO> getHighestRatingResumeBoard() {
		List<ResumeBoard> resumeBoards = resumeBoardRepository.findTop3ByOrderByRatingDesc();

		return resumeBoards.stream()
				.map(resumeBoard -> ResumeBoardDTO.builder()
						.resumeBoardNo(resumeBoard.getResumeBoardNo())
						.rating((float)Math.round(resumeBoard.getRating() * 10) / 10)
						.ratingCount(resumeBoard.getRatingCount())
						.readCount(resumeBoard.getReadCount())
						.title(resumeBoard.getTitle())
						.content(resumeBoard.getResume().getContent())
						.createdDate(resumeBoard.getResume().getCreatedDate())
						.build())
				.toList();
	}

	@Override
	public boolean isNotExistResumeBoard(long resumeBoardNo) {
		return !resumeBoardRepository.existsById(resumeBoardNo);
	}

	@Override
	public Page<Object[]> getBookmarkList(long userNo, Pageable pageable) {
		return resumeBoardRepository.getBookmarkList(userNo, pageable);
	}
}
