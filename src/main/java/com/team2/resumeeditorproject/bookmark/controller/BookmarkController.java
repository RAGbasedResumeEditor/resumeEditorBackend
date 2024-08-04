package com.team2.resumeeditorproject.bookmark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.resumeeditorproject.common.dto.response.CommonResponse;
import com.team2.resumeeditorproject.common.dto.response.ResultMessageResponse;
import com.team2.resumeeditorproject.exception.DataNotFoundException;
import com.team2.resumeeditorproject.bookmark.dto.BookmarkDTO;
import com.team2.resumeeditorproject.bookmark.service.BookmarkService;
import com.team2.resumeeditorproject.resume.service.ResumeBoardService;
import com.team2.resumeeditorproject.user.dto.UserDTO;

@RestController
@RequestMapping("/board")
public class BookmarkController {

	@Autowired
	private BookmarkService bookmarkService;

	@Autowired
	private ResumeBoardService resumeBoardService;

	@PostMapping("/bookmark")
	public ResponseEntity<ResultMessageResponse> saveBookmark(@RequestBody BookmarkDTO bookmarkDTO, UserDTO loginUser) {
		if (resumeBoardService.isNotExistResumeBoard(bookmarkDTO.getResumeBoardNo())) {
			throw new DataNotFoundException("ResumeBoard with num " + bookmarkDTO.getResumeBoardNo() + " not found");
		}

		return ResponseEntity
				.ok()
				.body(ResultMessageResponse.builder()
						.response(bookmarkService.toggleBookmark(bookmarkDTO, loginUser))
						.status("Success")
						.build());
	}

	/* 즐겨찾기 확인 */
	@GetMapping("/{resumeBoardNo}/bookmark/exist")
	public ResponseEntity<CommonResponse<Boolean>> isExistBookmark(@PathVariable("resumeBoardNo") Long resumeBoardNo, UserDTO loginUser) {
		if (resumeBoardService.isNotExistResumeBoard(resumeBoardNo)) {
			throw new DataNotFoundException("ResumeBoard with num " + resumeBoardNo + " not found");
		}

		return ResponseEntity
				.ok()
				.body(CommonResponse.<Boolean>builder()
						.response(bookmarkService.isExistBookmark(resumeBoardNo, loginUser))
						.status("Success")
						.build());
	}
}
