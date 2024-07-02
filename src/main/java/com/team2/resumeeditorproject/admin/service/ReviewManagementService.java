package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.review.domain.Review;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewManagementService {
    boolean selectReview(Long rvNum);
    Page<Review> getPagedReviews(int pageNo);
    Page<Review> getAllShows(int pageNo);
    List<Review> getVisibleReviews();
}
