package com.team2.resumeeditorproject.statistics.repository;

import com.team2.resumeeditorproject.statistics.domain.StatisticsHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsHistoryRepository extends JpaRepository<StatisticsHistory, Long> {

}
