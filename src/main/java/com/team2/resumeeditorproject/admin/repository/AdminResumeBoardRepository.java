package com.team2.resumeeditorproject.admin.repository;

import com.team2.resumeeditorproject.resume.domain.ResumeStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminResumeBoardRepository extends JpaRepository<ResumeStatistics, Long> {
    @EntityGraph(attributePaths = {"resume", "resume.user"})
    Page<ResumeStatistics> findByTitleContaining(String title, Pageable pageable);

    @EntityGraph(attributePaths = {"resume", "resume.user"})
    Page<ResumeStatistics> findByRatingBetween(Float rating1, Float rating2, Pageable pageable);

    @EntityGraph(attributePaths = {"resume", "resume.user"})
    @Query("SELECT rb FROM ResumeStatistics rb")
    Page<ResumeStatistics> findAllResumeBoards(Pageable pageable);
}
