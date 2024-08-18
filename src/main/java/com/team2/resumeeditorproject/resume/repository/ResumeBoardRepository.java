package com.team2.resumeeditorproject.resume.repository;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * resumeBoardRepository
 *
 * @author : 안은비
 * @fileName : ResumeBoardRepository
 * @since : 04/30/24
 */
public interface ResumeBoardRepository extends JpaRepository<ResumeBoard, Long> {
	Page<ResumeBoard> findAllByTitleContainingOrderByResumeBoardNoDesc(String title, Pageable pageable);

	List<ResumeBoard> findTop3ByOrderByReadCountDesc();

	List<ResumeBoard> findTop3ByOrderByRatingDesc();

	@Modifying
	@Query("UPDATE ResumeBoard SET readCount = readCount + 1 WHERE resumeBoardNo = :resumeBoardNo")
	void updateReadCount(@Param("resumeBoardNo") Long resumeBoardNo);

}
