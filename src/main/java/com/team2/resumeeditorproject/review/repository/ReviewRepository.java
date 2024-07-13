package com.team2.resumeeditorproject.review.repository;

import com.team2.resumeeditorproject.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Review findByUserUserNo(long userNo);
}
