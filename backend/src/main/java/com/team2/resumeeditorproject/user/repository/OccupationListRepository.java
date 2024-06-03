package com.team2.resumeeditorproject.user.repository;

import com.team2.resumeeditorproject.resume.domain.Occupation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OccupationListRepository extends JpaRepository<Occupation, String> {
}
