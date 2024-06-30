package com.team2.resumeeditorproject.admin.repository;

import com.team2.resumeeditorproject.admin.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

}
