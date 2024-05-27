package com.team2.resumeeditorproject.admin.repository;

import com.team2.resumeeditorproject.admin.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    @Query("SELECT SUM(h.traffic) FROM History h")
    Long findTotalTraffic();

    @Query("SELECT SUM(h.traffic) FROM History h WHERE DATE_FORMAT(h.w_date, '%Y-%m-%d') = :currentDate")
    Long findTrafficByCurrentDate(@Param("currentDate") String currentDate);

    @Query("SELECT h FROM History h WHERE h.w_date BETWEEN :startDate AND :endDate")
    List<History> findTrafficDataBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
