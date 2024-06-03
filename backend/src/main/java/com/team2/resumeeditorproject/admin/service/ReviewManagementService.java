package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.review.domain.Review;
import org.springframework.data.domain.Page;

public interface ReviewManagementService {
    boolean selectReview(Long rvNum);
    Page<Review> getAllReviews(int page);
    Page<Review> getAllShows(int page);

    //void selectReview(Long rvNum);
}
