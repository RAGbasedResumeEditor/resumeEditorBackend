package com.team2.resumeeditorproject.resume.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team2.resumeeditorproject.resume.domain.ResumeEdit;

/**
 * resumeEditRepository
 *
 * @author : 안은비
 * @fileName : ResumeEditRepository
 * @since : 04/25/24
 */
public interface ResumeEditRepository extends JpaRepository<ResumeEdit, Long> {
	List<ResumeEdit> findResumeEditsByResumeResumeNo(Long resumeNo);
}
