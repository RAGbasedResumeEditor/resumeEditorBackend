package com.team2.resumeeditorproject.admin.repository;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminResumeBoardRepository extends JpaRepository<ResumeBoard, Long> {
    Page<ResumeBoard> findByTitleContaining(String title, Pageable pageable);
    Page<ResumeBoard> findByRatingBetween(Float rating1, Float rating2, Pageable pageable);
    Page<ResumeBoard> findAll(Pageable pageable);

    @Query(value = "SELECT rb, rb.title, r.w_date, u.username " +
            "FROM ResumeBoard rb " +
            "JOIN Resume r ON rb.RNum = r.r_num " +
            "JOIN User u ON r.u_num = u.uNum")
    Page<Object[]> findAllResumeBoards(Pageable pageable);
}
