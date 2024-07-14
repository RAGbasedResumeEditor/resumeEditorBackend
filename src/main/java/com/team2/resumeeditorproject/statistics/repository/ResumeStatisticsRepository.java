package com.team2.resumeeditorproject.statistics.repository;

import com.team2.resumeeditorproject.resume.domain.Resume;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ResumeStatisticsRepository extends JpaRepository <Resume, Long> {
    // 일자별 첨삭 횟수
    @EntityGraph(attributePaths = "resumeEdit")
    @Query("SELECT resume FROM Resume resume WHERE resume.createdDate BETWEEN :startDate AND :endDate")
    List<Resume> findByWDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // 오늘 첨삭 수
    @Query("SELECT count(resume) FROM Resume resume WHERE DATE_FORMAT(resume.createdDate, '%Y-%m-%d') = :currentDate")
    int findResumeCountByCurrentDate(@Param("currentDate") String currentDate);
}
