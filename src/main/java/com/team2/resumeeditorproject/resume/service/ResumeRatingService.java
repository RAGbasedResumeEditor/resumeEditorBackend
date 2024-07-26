package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.dto.ResumeRatingDTO;
import com.team2.resumeeditorproject.user.dto.UserDTO;

public interface ResumeRatingService {
	ResumeRatingDTO getResumeRating(long resumeBoardNo, UserDTO loginUser);
}
