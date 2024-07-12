package com.team2.resumeeditorproject.admin.repository;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminResumeBoardRepository extends JpaRepository<ResumeBoard, Long> {
    @EntityGraph(attributePaths = {"resume", "resume.user"})
    Page<ResumeBoard> findByTitleContaining(String title, Pageable pageable);

    @EntityGraph(attributePaths = {"resume", "resume.user"})
    Page<ResumeBoard> findByRatingBetween(Float rating1, Float rating2, Pageable pageable);

    @EntityGraph(attributePaths = {"resume", "resume.user"})
    @Query("SELECT rb FROM ResumeBoard rb")
    Page<ResumeBoard> findAllResumeBoards(Pageable pageable);
}
