package com.team2.resumeeditorproject.resume.repository;

import com.team2.resumeeditorproject.resume.domain.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * resumeRepository
 *
 * @author : 안은비
 * @fileName : ResumeRepository
 * @since : 04/25/24
 */
public interface ResumeRepository extends JpaRepository<Resume, Long> {

}
