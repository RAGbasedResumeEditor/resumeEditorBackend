package com.team2.resumeeditorproject.resume.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.resumeeditorproject.common.dto.response.CommonListResponse;
import com.team2.resumeeditorproject.common.dto.response.CommonResponse;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import com.team2.resumeeditorproject.resume.dto.request.ResumeBoardRequest;
import com.team2.resumeeditorproject.resume.service.ResumeBoardService;

/**
 * resumeBoardController 자소서 게시판 컨트롤러
 *
 * @author : 안은비
 * @fileName : ResumeBoardController
 * @since : 04/30/24
 */
@RestController
@RequestMapping("/board")
public class ResumeBoardController {

	@Autowired
	private ResumeBoardService resumeBoardService;

	private static final int SIZE_OF_PAGE = 5;

	@GetMapping("/list")
	public ResponseEntity<CommonListResponse<ResumeBoardDTO>> getPagedResumeBoards(@ModelAttribute ResumeBoardRequest resumeBoardRequest) {
		resumeBoardRequest.setPageSize(SIZE_OF_PAGE);
		Page<ResumeBoardDTO> resumeBoards = resumeBoardService.getPagedResumeBoardsContainingTitle(resumeBoardRequest);

		return ResponseEntity
				.ok()
				.body(CommonListResponse.<ResumeBoardDTO>builder()
						.status("Success")
						.response(resumeBoards.toList())
						.totalPages(resumeBoards.getTotalPages())
						.build());
	}

	@GetMapping("/{resumeBoardNo}")
	public ResponseEntity<CommonResponse<ResumeBoardDTO>> getResumeBoard(@PathVariable("resumeBoardNo") Long resumeBoardNo) {
		return ResponseEntity
				.ok()
				.body(CommonResponse.<ResumeBoardDTO>builder()
						.status("Success")
						.response(resumeBoardService.getResumeBoard(resumeBoardNo))
						.build());
	}

	@GetMapping("/list/rank/read-count")
	public ResponseEntity<CommonListResponse<ResumeBoardDTO>> getHighestReadCountResumeBoard() {
		return ResponseEntity
				.ok()
				.body(CommonListResponse.<ResumeBoardDTO>builder()
						.status("Success")
						.response(resumeBoardService.getHighestReadCountResumeBoard())
						.build());
	}

	@GetMapping("/list/rank/rating")
	public ResponseEntity<CommonListResponse<ResumeBoardDTO>> getHighestRatingResumeBoard() {
		return ResponseEntity
				.ok()
				.body(CommonListResponse.<ResumeBoardDTO>builder()
						.status("Success")
						.response(resumeBoardService.getHighestRatingResumeBoard())
						.build());
	}
}
