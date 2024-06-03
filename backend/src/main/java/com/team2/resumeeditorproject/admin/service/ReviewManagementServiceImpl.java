package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.domain.Review;
import com.team2.resumeeditorproject.admin.repository.AdminReviewRepository;
import com.team2.resumeeditorproject.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ReviewManagementServiceImpl implements ReviewManagementService{

    @Autowired
    AdminReviewRepository reviewRepository;

    @Override
    public boolean selectReview(Long rvNum) {
        Optional<Review> optionalReview = reviewRepository.findById(rvNum);
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            if (review.isShow()) {
                return false; // 이미 선택된 경우 false 반환
            } else {
                review.setShow(true); // 선택되지 않은 경우 show 값을 true로 변경
                reviewRepository.save(review);
                return true; // 선택된 경우 true 반환
            }
        } else {
            throw new BadRequestException("Review with id " + rvNum + " not found");
        }
    }
}
