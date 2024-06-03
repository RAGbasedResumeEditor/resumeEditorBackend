package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminReviewRepository;

import com.team2.resumeeditorproject.exception.BadRequestException;

import com.team2.resumeeditorproject.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewManagementServiceImpl implements  ReviewManagementService {

    private final AdminReviewRepository reviewRepository;

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

    @Override
    @Transactional(readOnly = true)
    public Page<Review> getAllReviews(int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Review> pageResult = reviewRepository.findAll(pageable);
        return pageResult;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Review> getAllShows(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("rvNum").descending());
        Page<Review> pageResult = reviewRepository.findByShow(pageable);
        return pageResult;
    }

    @Override
    public List<Review> getVisibleReviews() {
        return reviewRepository.findAllByShow(true);
    }
}

