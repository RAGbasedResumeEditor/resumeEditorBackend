package com.team2.resumeeditorproject.review.service;

import com.team2.resumeeditorproject.review.dto.ReviewDTO;
import com.team2.resumeeditorproject.user.dto.UserDTO;

public interface ReviewService {

    void saveReview(ReviewDTO reviewDTO, UserDTO userNo);

    boolean isAlreadyReviewed(UserDTO userNo);
}
