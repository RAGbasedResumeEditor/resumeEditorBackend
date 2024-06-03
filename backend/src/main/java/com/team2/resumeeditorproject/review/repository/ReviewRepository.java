package com.team2.resumeeditorproject.review.repository;

import com.team2.resumeeditorproject.resume.domain.Resume;
import com.team2.resumeeditorproject.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Review findByUNum(long uNum);
}
