package com.team2.resumeeditorproject.statistics.repository;

import com.team2.resumeeditorproject.statistics.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

}
