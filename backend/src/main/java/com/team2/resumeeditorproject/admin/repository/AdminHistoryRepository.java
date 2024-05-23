package com.team2.resumeeditorproject.admin.repository;


import com.team2.resumeeditorproject.admin.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminHistoryRepository extends JpaRepository<History, Long> {
}
