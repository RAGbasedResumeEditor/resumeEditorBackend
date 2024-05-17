package com.team2.resumeeditorproject.admin.repository;

import com.team2.resumeeditorproject.resume.domain.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminResumeRepository extends JpaRepository<Resume, Long> {
    // 월 별 첨삭 횟수
    @Query(value = "SELECT DATE_FORMAT(r.w_date, '%Y-%m') AS month, COUNT(*) AS count " +
            "FROM resume r " +
            "GROUP BY DATE_FORMAT(r.w_date, '%Y-%m')", nativeQuery = true)
    List<Object[]> findMonthlyCorrectionCounts();

    // 월,주차 별 첨삭 횟수
    @Query(value = "SELECT DATE_FORMAT(r.w_date, '%Y-%m') AS month, " +
            "WEEK(r.w_date, 5) - WEEK(DATE_SUB(r.w_date, INTERVAL DAYOFMONTH(r.w_date) - 1 DAY), 5) + 1 AS week_of_month, " +
            "COUNT(*) AS count " +
            "FROM resume r " +
            "GROUP BY DATE_FORMAT(r.w_date, '%Y-%m'), week_of_month", nativeQuery = true)
    List<Object[]> findWeeklyCorrectionCounts();

    // 일 별 첨삭 횟수
    @Query(value = "SELECT DATE_FORMAT(r.w_date, '%Y-%m-%d') AS month, COUNT(*) AS count " +
            "FROM resume r " +
            "GROUP BY DATE_FORMAT(r.w_date, '%Y-%m-%d')", nativeQuery = true)
    List<Object[]> findDailyCorrectionCounts();
}
