package com.team2.resumeeditorproject.resume.repository;

import com.team2.resumeeditorproject.resume.domain.Items;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsRepository extends JpaRepository<Items, Long> {
    List<Items> findByCompanyContaining(String company);
}
