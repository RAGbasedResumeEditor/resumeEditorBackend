package com.team2.resumeeditorproject.resume.repository;

import com.team2.resumeeditorproject.resume.domain.ResumeRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<ResumeRating, Long> {
    int countByResumeBoardResumeBoardNoAndUserUserNo(long resumeBoardNo, long userNo);

    ResumeRating findByResumeBoardResumeBoardNoAndUserUserNo(long resumeBoardNo, long userNo);
}
