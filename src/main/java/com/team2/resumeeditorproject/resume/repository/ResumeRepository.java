package com.team2.resumeeditorproject.resume.repository;

import com.team2.resumeeditorproject.resume.domain.Resume;
import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
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
    @Query("SELECT re, r.content, r.createdDate\n" +
            "FROM ResumeEdit re\n" +
            "JOIN Resume r ON re.resumeEditNo = r.resumeNo\n" +
            "WHERE r.resumeNo = :resumeNo")
    Object getResumeEditDetail(@Param("resumeNo") Long resumeNo);

    @Query("SELECT re.resumeEditNo, re.company, re.occupation, re.mode, r.createdDate " +
            "FROM User u " +
            "INNER JOIN u.resumeEdits re " +
            "INNER JOIN Resume r ON re.resumeEditNo = r.resumeNo " +
            "WHERE u.userNo = :userNo " +
            "ORDER BY r.resumeNo DESC")
    Page<Object[]> getMyPageEditList(@Param("userNo") long userNo, Pageable pageable);
}
