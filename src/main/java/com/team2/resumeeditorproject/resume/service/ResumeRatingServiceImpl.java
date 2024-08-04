package com.team2.resumeeditorproject.resume.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team2.resumeeditorproject.exception.DataNotFoundException;
import com.team2.resumeeditorproject.resume.domain.ResumeRating;
import com.team2.resumeeditorproject.resume.dto.ResumeRatingDTO;
import com.team2.resumeeditorproject.resume.repository.RatingRepository;
import com.team2.resumeeditorproject.user.dto.UserDTO;

@Service
public class ResumeRatingServiceImpl implements ResumeRatingService {
	@Autowired
	private RatingRepository ratingRepository;

	public ResumeRatingDTO getResumeRating(long resumeBoardNo, UserDTO loginUser) {
		ResumeRating resumeRating = ratingRepository.findByResumeBoardResumeBoardNoAndUserUserNo(resumeBoardNo, loginUser.getUserNo())
				.orElseThrow(() -> new DataNotFoundException("can't find rating"));

		return ResumeRatingDTO.builder()
				.rating(resumeRating.getRating())
				.build();
	}
}
