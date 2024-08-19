package com.team2.resumeeditorproject.resume.repository;

import com.team2.resumeeditorproject.resume.domain.ResumeGuide;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResumeGuideRepository extends JpaRepository<ResumeGuide, Long> {
    Page<ResumeGuide> findByUserUserNoOrderByResumeGuideNoDesc(Long userNo, Pageable pageable);
}
