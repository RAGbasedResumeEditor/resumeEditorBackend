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
    @Query("SELECT re.r_num, re.company, re.occupation, re.mode, r.w_date " +
            "FROM ResumeEdit re " +
            "JOIN Resume r ON re.r_num = r.r_num " +
            "WHERE re.u_num = :u_num " +
            "ORDER BY r.r_num DESC")
    Page<Object[]> getMyPageEditList(@Param("u_num") long u_num, Pageable pageable);
}
