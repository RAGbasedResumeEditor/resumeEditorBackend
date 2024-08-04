package com.team2.resumeeditorproject.resume.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team2.resumeeditorproject.exception.DataNotFoundException;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.domain.ResumeRating;
import com.team2.resumeeditorproject.resume.dto.ResumeRatingDTO;
import com.team2.resumeeditorproject.resume.repository.RatingRepository;
import com.team2.resumeeditorproject.resume.repository.ResumeBoardRepository;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.UserRepository;

@Service
public class ResumeRatingServiceImpl implements ResumeRatingService {
	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private ResumeBoardRepository resumeBoardRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public ResumeRatingDTO getResumeRating(long resumeBoardNo, UserDTO loginUser) {
		ResumeRating resumeRating = ratingRepository.findByResumeBoardResumeBoardNoAndUserUserNo(resumeBoardNo, loginUser.getUserNo())
				.orElseThrow(() -> new DataNotFoundException("can't find rating"));

		return ResumeRatingDTO.builder()
				.rating(resumeRating.getRating())
				.build();
	}

	@Override
	public void saveResumeRating(ResumeRatingDTO resumeRatingDTO, UserDTO loginUser) {
		if (resumeRatingDTO.getRating() < 0 || resumeRatingDTO.getRating() > 5) {
			throw new IllegalArgumentException("rating must be between 0 and 5");
		}

		ResumeBoard resumeBoard = resumeBoardRepository.findById(resumeRatingDTO.getResumeBoardNo())
				.orElseThrow(() -> new IllegalArgumentException("invalid resumeBoardNo"));

		if (ratingRepository.existsByResumeBoardResumeBoardNo(resumeRatingDTO.getResumeBoardNo())) {
			throw new IllegalStateException("is already rated");
		}

		// 별점을 안 준 상태면 rating 테이블에 저장
		ResumeRating resumeRating = ResumeRating.builder()
				.resumeBoard(resumeBoard)
				.user(userRepository.findById(loginUser.getUserNo()).orElseThrow(() -> new IllegalArgumentException("Invalid user ID")))
				.rating(resumeRatingDTO.getRating())
				.build();

		ratingRepository.save(resumeRating);
	}
}
