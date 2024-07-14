package com.team2.resumeeditorproject.statistics.repository;

import com.team2.resumeeditorproject.statistics.domain.Traffic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrafficRepository extends JpaRepository<Traffic, Long> {
    Traffic findByInDate(LocalDate inDate);

    // 총 방문자 수
    @Query("SELECT SUM(traffic.visitCount) FROM Traffic traffic")
    long sumAllTraffic();

    // 일별 접속자 수 집계
    List<Traffic> findByInDateBetween(LocalDate startDate, LocalDate endDate);

    // 오늘 방문자 수
    long countByInDate(LocalDate inDate);
}
