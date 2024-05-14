package com.team2.resumeeditorproject.admin.repository;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminResumeBoardRepository extends JpaRepository<ResumeBoard, Long> {
    List<ResumeBoard> findByTitleContaining(String title, PageRequest pageRequest);
    List<ResumeBoard> findByRatingBetween(Float rating1, Float rating2);
    Page<ResumeBoard> findAll(Pageable pageable);
}
