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
    List<ResumeEdit> findByCompany(String company);
    List<ResumeEdit> findByOccupation(String occupation);

    // 첨삭 횟수
    @Query("SELECT COUNT(r) FROM ResumeEdit r WHERE r.u_num = :u_num")
    int countByUNum(@Param("u_num") long uNum);

    // 첨삭 횟수(수정)
    @Query("SELECT resumeEdit.u_num, COUNT(resumeEdit) FROM ResumeEdit resumeEdit WHERE resumeEdit.u_num IN :u_num GROUP BY resumeEdit.u_num")
    List<Object[]> countByUNumIn(@Param("u_num") List<Long> uNum);

    @Query("SELECT COUNT(r) FROM ResumeEdit r")
    int countRecords();

    // 첨삭 횟수 통계
    @Query("SELECT DISTINCT r.occupation FROM ResumeEdit r")
    List<String> findOccupations();

    // 첨삭 받기 위한 지원직종별 첨삭횟수 랭킹Top5
    @Query(value = "SELECT occupation, COUNT(occupation) AS count " +
            "FROM resume_edit " +
            "GROUP BY occupation " +
            "ORDER BY count DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5Occupations();

    @Query("SELECT DISTINCT r.company FROM ResumeEdit r")
    List<String> findCompanies();

    // 첨삭 받기 위한 지원회사별 첨삭횟수 랭킹Top5
    @Query(value = "SELECT company, COUNT(company) AS count " +
            "FROM resume_edit " +
            "GROUP BY company " +
            "ORDER BY count DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5Companies();
}
