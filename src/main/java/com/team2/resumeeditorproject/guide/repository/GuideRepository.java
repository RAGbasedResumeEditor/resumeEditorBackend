package com.team2.resumeeditorproject.guide.repository;

import java.util.Optional;

import com.team2.resumeeditorproject.guide.domain.Guide;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideRepository extends JpaRepository<Guide, Long> {
    Optional<Guide> findByUserUserNo(long userNo);
}
