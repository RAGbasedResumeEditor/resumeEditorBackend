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
    public String selectReview(Long reviewNo) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewNo);
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            if (review.getDisplay().equals("true")) {
                review.setDisplay("false"); // 이미 선택된 경우 false로 변경(display 된 리뷰에서 취소)
                reviewRepository.save(review);
                return "Unchecked from display review.";
            } else {
                review.setDisplay("true"); // 선택되지 않은 경우 show 값을 true로 변경
                reviewRepository.save(review);
                return "Checked from display review.";
            }
        } else {
            throw new BadRequestException("Review with id " + reviewNo + " not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Review> getPagedReviews(int pageNo){
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by("rvNum").descending());
        Page<Review> pageResult = reviewRepository.findAll(pageable);
        return pageResult;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Review> getDisplayReviews(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by("rvNum").descending());
        Page<Review> pageResult = reviewRepository.findByDisplay(pageable);
        return pageResult;
    }

    @Override
    public List<Review> getVisibleReviews() {
        return reviewRepository.findAllByDisplay("true");
    }
}

