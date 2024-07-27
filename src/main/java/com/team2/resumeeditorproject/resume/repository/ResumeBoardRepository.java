package com.team2.resumeeditorproject.resume.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;

/**
 * resumeBoardRepository
 *
 * @author : 안은비
 * @fileName : ResumeBoardRepository
 * @since : 04/30/24
 */
public interface ResumeBoardRepository extends JpaRepository<ResumeBoard, Long> {
	Page<ResumeBoard> findAllByTitleContaining(String title, Pageable pageable);

	List<ResumeBoard> findTop3ByOrderByReadCountDesc();

	List<ResumeBoard> findTop3ByOrderByRatingDesc();

	@Query("UPDATE ResumeBoard SET readCount = readCount + 1 WHERE resumeBoardNo = :resumeBoardNo")
	void updateReadCount(Long resumeBoardNo);

	@Query("SELECT rb, r.content, r.createdDate " +
			"FROM ResumeBoard rb " +
			"JOIN Resume r " +
			"JOIN Bookmark b " +
			"JOIN User u " +
			"WHERE u.userNo = :userNo " +
			"ORDER BY b.bookmarkNo DESC")
	Page<Object[]> getBookmarkList(@Param("userNo") long userNo, Pageable pageable);
}
