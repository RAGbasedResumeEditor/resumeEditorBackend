package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.domain.Review;
import org.springframework.data.domain.Page;

public interface ReviewManagementService {
    Page<Review> getAllReviews(int page);
    Page<Review> getAllShows(int page);

    void selectReview(Long rvNum);
}
