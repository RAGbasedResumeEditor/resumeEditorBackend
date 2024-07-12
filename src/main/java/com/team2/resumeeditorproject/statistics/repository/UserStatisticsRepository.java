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
    List<User> findByInDateBetween(Date startDate, Date endDate);

    // 직종 랭킹 Top5
    @Query(value = "SELECT occupation, COUNT(occupation) AS count " +
            "FROM user " +
            "GROUP BY occupation " +
            "ORDER BY count DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5Occupations();

    // 회사 랭킹 Top5
    @Query(value = "SELECT company, COUNT(company) AS count " +
            "FROM user " +
            "GROUP BY company " +
            "ORDER BY count DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5Companies();

    // 희망직종 랭킹 Top5
    @Query(value = "SELECT wish, COUNT(wish) AS count " +
            "FROM user " +
            "GROUP BY wish " +
            "ORDER BY count DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5Wishes();
}
