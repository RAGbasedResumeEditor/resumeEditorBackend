package com.team2.resumeeditorproject.resume.repository;

import java.util.Optional;

import com.team2.resumeeditorproject.resume.domain.ResumeRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<ResumeRating, Long> {
    Optional<ResumeRating> findByResumeBoardResumeBoardNoAndUserUserNo(long resumeBoardNo, long userNo);
}
