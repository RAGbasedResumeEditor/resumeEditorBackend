package com.team2.resumeeditorproject.admin.repository;

import com.team2.resumeeditorproject.resume.domain.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminResumeRepository extends JpaRepository<Resume, Long> {

}
