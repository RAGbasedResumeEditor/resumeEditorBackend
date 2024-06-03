package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewManagementServiceImpl implements ReviewManagementService{

    @Autowired
    AdminReviewRepository reviewRepository;

    @Override
    public void selectReview(Long rvNum) {
        reviewRepository.findById(rvNum).ifPresent(review -> {
            review.setShow(true);
            reviewRepository.save(review);
        });
    }
}
