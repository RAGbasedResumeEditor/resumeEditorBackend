package com.team2.resumeeditorproject.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.resumeeditorproject.common.dto.response.CommonResponse;
import com.team2.resumeeditorproject.review.dto.ReviewDTO;
import com.team2.resumeeditorproject.review.service.ReviewService;
import com.team2.resumeeditorproject.user.dto.UserDTO;

@RestController
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	/* 리뷰 달기 */
	@PostMapping
	public ResponseEntity<CommonResponse<String>> saveReview(@RequestBody ReviewDTO reviewDTO, UserDTO loginUser) {
		reviewService.saveReview(reviewDTO, loginUser);

		return ResponseEntity.ok()
				.body(CommonResponse.<String>builder()
						.response("review table insert success")
						.status("Success")
						.build());
	}

	@GetMapping("/exists")
	public ResponseEntity<CommonResponse<String>> checkReviewExists(UserDTO loginUser) {
		return ResponseEntity.ok()
				.body(CommonResponse.<String>builder()
						.response(String.valueOf(reviewService.isAlreadyReviewed(loginUser)))
						.status("Success")
						.build());
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<CommonResponse<String>> handleException(IllegalStateException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(CommonResponse.<String>builder()
						.response(exception.getMessage())
						.status("Fail")
						.build());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<CommonResponse<String>> handleException(IllegalArgumentException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(CommonResponse.<String>builder()
						.response(exception.getMessage())
						.status("Fail")
						.build());
	}
}
