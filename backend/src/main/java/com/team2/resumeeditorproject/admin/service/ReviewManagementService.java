package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.review.domain.Review;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewManagementService {
    boolean selectReview(Long rvNum);
    Page<Review> getAllReviews(int page);
    Page<Review> getAllShows(int page);
    List<Review> getVisibleReviews();
}
