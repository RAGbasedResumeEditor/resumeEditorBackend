package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.domain.Review;
import com.team2.resumeeditorproject.admin.repository.AdminReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewManagementServiceImpl implements  ReviewManagementService {

    private final AdminReviewRepository reviewRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Review> getAllShows(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("rv_num").descending());
        Page<Review> pageResult = reviewRepository.findByShow(pageable);
        return pageResult;
    }

        @Override
        public void selectReview (Long rvNum){
            reviewRepository.findById(rvNum).ifPresent(review -> {
                review.setShow(true);
                reviewRepository.save(review);
            });
        }
    }
