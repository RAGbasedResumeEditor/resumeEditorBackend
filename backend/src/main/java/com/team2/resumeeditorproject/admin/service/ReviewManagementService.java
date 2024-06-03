package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface ReviewManagementService {
    @Transactional(readOnly = true)
    Page<Review> getAllShows(int page);

    void selectReview(Long rvNum);
}
