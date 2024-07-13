package com.team2.resumeeditorproject.statistics.repository;

import com.team2.resumeeditorproject.statistics.domain.DailyStatistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyStatisticsRepository extends JpaRepository<DailyStatistics, Long> {
    DailyStatistics findByReferenceDate(LocalDate inDate);

    // 총 방문자 수
    @Query("SELECT SUM(dailyStatistics.visitCount) FROM DailyStatistics dailyStatistics")
    long sumAllTraffic();

    // 일별 접속자 수 집계
    List<DailyStatistics> findByReferenceDateBetween(LocalDate startDate, LocalDate endDate);

    // 오늘 방문자 수
    long countByReferenceDate(LocalDate inDate);
}
