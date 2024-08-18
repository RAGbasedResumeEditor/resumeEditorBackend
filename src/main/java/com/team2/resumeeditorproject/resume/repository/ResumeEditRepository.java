package com.team2.resumeeditorproject.resume.repository;

import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * resumeEditRepository
 *
 * @author : 안은비
 * @fileName : ResumeEditRepository
 * @since : 04/25/24
 */
public interface ResumeEditRepository extends JpaRepository<ResumeEdit, Long> {
	List<ResumeEdit> findResumeEditsByResumeEditNo(Long resumeNo);


	Page<ResumeEdit> findAllByUserUserNoOrderByResumeEditNoDesc(long userNo, Pageable pageable);

}
