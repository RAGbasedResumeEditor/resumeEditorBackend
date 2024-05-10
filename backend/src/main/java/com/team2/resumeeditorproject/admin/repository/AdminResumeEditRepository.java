package com.team2.resumeeditorproject.admin.repository;

import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminResumeEditRepository extends JpaRepository<ResumeEdit, Long> {
    // 유저 첨삭횟수
    @Query("SELECT COUNT(re) FROM ResumeEdit re WHERE re.u_num = :u_num")
    int countByRNum(@Param("u_num") Long uNum);
}
