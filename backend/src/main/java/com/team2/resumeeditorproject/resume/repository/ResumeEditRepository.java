package com.team2.resumeeditorproject.resume.repository;

import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * resumeEditRepository
 *
 * @author : 안은비
 * @fileName : ResumeEditRepository
 * @since : 04/25/24
 */
public interface ResumeEditRepository extends JpaRepository<ResumeEdit, Long> {
}
