package com.team2.resumeeditorproject.resume.repository;

import com.team2.resumeeditorproject.resume.domain.Occupation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OccupationRepository extends JpaRepository<Occupation, Long> {
    List<Occupation> findByOccupationNameContaining(String occupationName);
}
