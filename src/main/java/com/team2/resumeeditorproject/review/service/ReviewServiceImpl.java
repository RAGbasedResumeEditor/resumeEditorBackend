package com.team2.resumeeditorproject.review.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.team2.resumeeditorproject.review.domain.Review;
import com.team2.resumeeditorproject.review.dto.ReviewDTO;
import com.team2.resumeeditorproject.review.repository.ReviewRepository;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.UserRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void saveReview(ReviewDTO reviewDTO, UserDTO userDTO) {
		if (reviewRepository.existsByUserUserNo(userDTO.getUserNo())) {
			throw new IllegalStateException("리뷰를 이미 작성한 사용자입니다");
		}

		if (reviewDTO.getContent().length() > 200) {
			throw new IllegalArgumentException("리뷰는 최대 200자까지 작성할 수 있습니다");
		}

		Review newReview = Review.builder()
				.user(userRepository.findById(userDTO.getUserNo()).orElseThrow(() -> new UsernameNotFoundException("잘못된 사용자입니다")))
				.content(reviewDTO.getContent())
				.rating(reviewDTO.getRating())
				.createdDate(new Date())
				.build();

		reviewRepository.save(newReview);
	}

	@Override
	public boolean isAlreadyReviewed(UserDTO userDTO) {
		return reviewRepository.existsByUserUserNo(userDTO.getUserNo());
	}
}
