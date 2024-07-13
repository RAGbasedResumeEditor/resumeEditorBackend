package com.team2.resumeeditorproject.statistics.repository;

import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeEditStatisticsRepository extends JpaRepository<ResumeEdit, Long> {
    // 첨삭 횟수
    // @Query("SELECT resumeEdit.userNo, COUNT(resumeEdit) FROM ResumeEdit resumeEdit WHERE resumeEdit.userNo IN :userNo GROUP BY resumeEdit.userNo")


    @Query("SELECT user.userNo, COUNT(resumeEdit) FROM ResumeEdit resumeEdit INNER JOIN User user WHERE user.userNo IN :userNo GROUP BY user.userNo")
    List<Object[]> countResumeEditByUserIn(@Param("userNo") List<Long> userNo);

    // 첨삭 받기 위한 지원직종별 첨삭횟수 랭킹Top5
    @Query(value = "SELECT occupation, COUNT(occupation) AS count " +
            "FROM resume_edit " +
            "GROUP BY occupation " +
            "ORDER BY count DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5Occupations();

    // 첨삭 받기 위한 지원회사별 첨삭횟수 랭킹Top5
    @Query(value = "SELECT company, COUNT(company) AS count " +
            "FROM resume_edit " +
            "GROUP BY company " +
            "ORDER BY count DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5Companies();
}
