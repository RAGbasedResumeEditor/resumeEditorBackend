package com.team2.resumeeditorproject.statistics.repository;

import com.team2.resumeeditorproject.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserStatisticsRepository extends JpaRepository<User, Long> {
    // 사용자 수 조회
    @Query("SELECT COUNT(user) FROM User user")
    int countUsers();

    List<User> findByGender(char gender);
    List<User> findByAgeBetween(int startAge, int endAge);
    List<User> findByStatus(int status);
    List<User> findByMode(int mode);
    List<User> findByCreatedDateBetween(Date startDate, Date endDate);

    // 직종 랭킹 Top5
    @Query(value = "SELECT o.occupation_name, COUNT(u.occupation_no) AS count " +
            "FROM user u " +
            "JOIN occupation o ON u.occupation_no = o.occupation_no " +
            "GROUP BY o.occupation_name " +
            "ORDER BY count DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5Occupations();

    // 회사 랭킹 Top5
    @Query(value = "SELECT c.company_name, COUNT(u.company_no) AS count " +
            "FROM user u " +
            "JOIN company c ON u.company_no = c.company_no " +
            "GROUP BY c.company_name " +
            "ORDER BY count DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5Companies();

    // 희망직종 랭킹 Top5
    @Query(value = "SELECT c.company_name, COUNT(u.wish_company_no) AS count " +
            "FROM user u " +
            "JOIN company c ON u.company_no = c.company_no " +
            "GROUP BY company_name " +
            "ORDER BY count DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5Wishes();
}
