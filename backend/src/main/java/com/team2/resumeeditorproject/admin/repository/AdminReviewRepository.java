package com.team2.resumeeditorproject.admin.repository;

import com.team2.resumeeditorproject.admin.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminReviewRepository extends JpaRepository<Review, Long> {
}
