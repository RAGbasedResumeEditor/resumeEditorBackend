package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.dto.LandingPageReviewDTO;
import com.team2.resumeeditorproject.review.domain.Review;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewManagementService {
    String selectReview(Long reviewNo);
    Page<Review> getPagedReviews(int pageNo);
    Page<Review> getDisplayReviews(int pageNo);
    List<LandingPageReviewDTO> getVisibleReviews();
}
