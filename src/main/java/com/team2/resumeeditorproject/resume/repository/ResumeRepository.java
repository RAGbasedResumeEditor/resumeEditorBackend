package com.team2.resumeeditorproject.resume.repository;

import com.team2.resumeeditorproject.resume.domain.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * resumeRepository
 *
 * @author : 안은비
 * @fileName : ResumeRepository
 * @since : 04/25/24
 */
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    @Query("SELECT re.resumeEditNo, re.company, re.occupation, re.mode, r.createdDate " +
            "FROM ResumeEdit re " +
            "JOIN Resume r " +
            "WHERE re.user.userNo = :userNo " +
            "ORDER BY r.resumeNo DESC")
    Page<Object[]> getMyPageEditList(@Param("userNo") long userNo, Pageable pageable);
}
