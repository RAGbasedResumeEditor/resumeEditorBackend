package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.exception.NotFoundException;
import com.team2.resumeeditorproject.review.dto.ReviewDTO;
import com.team2.resumeeditorproject.admin.repository.AdminReviewRepository;
import com.team2.resumeeditorproject.common.util.PageUtil;
import com.team2.resumeeditorproject.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewManagementServiceImpl implements  ReviewManagementService {

    private final AdminReviewRepository reviewRepository;

    private Page<ReviewDTO> convertToReviewDTOPage(Page<Review> resultPage) {
        List<ReviewDTO> reviewDTOList = resultPage.stream()
                .map(review -> new ReviewDTO(
                        review.getReviewNo(),
                        review.getUser().getUserNo(),
                        review.getContent(),
                        review.getRating(),
                        review.getMode(),
                        review.getDisplay(),
                        review.getCreatedDate()
                ))
                .collect(Collectors.toList());
        return new PageImpl<>(reviewDTOList, resultPage.getPageable(), resultPage.getTotalElements());
    }

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
            throw new NotFoundException("Review with id " + reviewNo + " not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDTO> getPagedReviews(int pageNo, int size) {
        // page가 0보다 작으면 재요청
        PageUtil.checkUnderZero(pageNo);

        Pageable pageable = PageRequest.of(pageNo, size, Sort.by("rvNum").descending());

        Page<Review> reviewPage = reviewRepository.findAll(pageable);
        Page<ReviewDTO> reviewDTOPage = convertToReviewDTOPage(reviewPage);

        // 결과가 없는 경우
        PageUtil.checkListEmpty(reviewDTOPage);

        // page가 totalPages보다 크면 재요청
        int lastPageNo = reviewDTOPage.getTotalPages() - 1;
        PageUtil.checkExcessLastPageNo(pageNo, lastPageNo);

        return reviewDTOPage;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDTO> getDisplayReviews(int pageNo, int size) {
        // page가 0보다 작으면 재요청
        PageUtil.checkUnderZero(pageNo);

        Pageable pageable = PageRequest.of(pageNo, size, Sort.by("rvNum").descending());

        Page<Review> reviewPage = reviewRepository.findByDisplay(pageable);
        Page<ReviewDTO> reviewDTOPage = convertToReviewDTOPage(reviewPage);

        // 결과가 없는 경우
        PageUtil.checkListEmpty(reviewDTOPage);

        // page가 totalPages보다 크면 재요청
        int lastPageNo = reviewDTOPage.getTotalPages() - 1;
        PageUtil.checkExcessLastPageNo(pageNo, lastPageNo);

        return reviewDTOPage;
    }

    @Override
    public List<ReviewDTO> getVisibleReviews() {
        List<Review> visibleReviews = reviewRepository.findAllByDisplay("true");

        return visibleReviews.stream()
                .map(review -> ReviewDTO.builder()
                        .reviewNo(review.getReviewNo())
                        .userNo(review.getUser().getUserNo())
                        .content(review.getContent())
                        .rating(review.getRating())
                        .mode(review.getMode())
                        .display(review.getDisplay())
                        .createdDate(review.getCreatedDate())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
