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


    @Query("SELECT user.userNo, COUNT(resumeEdit) FROM ResumeEdit resumeEdit INNER JOIN User user ON resumeEdit.user = user WHERE user.userNo IN :userNo GROUP BY user.userNo")
    List<Object[]> countResumeEditByUserIn(@Param("userNo") List<Long> userNo);

    // 첨삭 받기 위한 지원직종별 첨삭횟수 랭킹Top5
    @Query(value = "SELECT occupation.occupation_name, COUNT(resumeEdit.occupation_no) AS count " +
            "FROM resume_edit resumeEdit " +
            "JOIN occupation occupation ON resumeEdit.occupation_no = occupation.occupation_no " +
            "GROUP BY occupation.occupation_name " +
            "ORDER BY count DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5Occupations();

    // 첨삭 받기 위한 지원회사별 첨삭횟수 랭킹Top5
    @Query(value = "SELECT company.company_name, COUNT(resumeEdit.company_no) AS count " +
            "FROM resume_edit resumeEdit " +
            "JOIN company company ON resumeEdit.company_no = company.company_no " +
            "GROUP BY company.company_name " +
            "ORDER BY count DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5Companies();
}
