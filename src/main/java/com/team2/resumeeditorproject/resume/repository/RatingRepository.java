package com.team2.resumeeditorproject.resume.repository;

import com.team2.resumeeditorproject.resume.domain.ResumeRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<ResumeRating, Long> {
    Optional<ResumeRating> findByResumeBoardResumeBoardNoAndUserUserNo(long resumeBoardNo, long userNo);

    boolean existsByResumeBoardResumeBoardNoAndUserUserNo(long resumeBoardNo, long userNo);

    @Modifying
    @Query("UPDATE ResumeBoard SET ratingCount = :newRatingCount, rating = :newRating WHERE resumeBoardNo = :resumeBoardNo")
    int updateRatingCount(@Param("resumeBoardNo") Long resumeBoardNo, @Param("newRatingCount") int newRatingCount, @Param("newRating") double newRating);
}
