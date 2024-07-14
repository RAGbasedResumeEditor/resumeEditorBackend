package com.team2.resumeeditorproject.resume.repository;

import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * resumeEditRepository
 *
 * @author : 안은비
 * @fileName : ResumeEditRepository
 * @since : 04/25/24
 */
public interface ResumeEditRepository extends JpaRepository<ResumeEdit, Long> {
    @Query("SELECT re FROM ResumeEdit re JOIN re.resume r WHERE r.r_num = :r_num")
    List<ResumeEdit> findResumeEditsByRNum(@Param("r_num") Long r_num);
}
