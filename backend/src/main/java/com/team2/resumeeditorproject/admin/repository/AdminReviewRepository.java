package com.team2.resumeeditorproject.admin.repository;

import com.team2.resumeeditorproject.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAll(Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.show=true")
    Page<Review> findByShow(Pageable pageable);

}
