package com.team2.resumeeditorproject.admin.repository;

import com.team2.resumeeditorproject.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AdminUserRepository extends JpaRepository<User, Long> {
    // 사용자 수 조회
    @Query("SELECT COUNT(user) FROM User user")
    int countUsers();

    // 회원 조회 + 페이징
    @EntityGraph(attributePaths = {"resumeEdits"})
    @Query("SELECT user FROM User user")
    Page<User> findAllUsersWithResumeEdits(Pageable pageable);

    // 회원 정보 + 첨삭 횟수
    @Query("SELECT u, COUNT(re) FROM User u LEFT JOIN u.resumeEdits re GROUP BY u ORDER BY u.inDate DESC")
    Page<Object[]> findUsersWithResumeEditCount(Pageable pageable);

    // 유저 수 통계
    @Query("SELECT DISTINCT u.occupation FROM User u WHERE u.occupation IS NOT NULL")
    List<String> findOccupations();

    // 직종 랭킹 Top5(수정)
    @Query(value = "SELECT occupation, COUNT(occupation) AS count " +
            "FROM user " +
            "GROUP BY occupation " +
            "ORDER BY count DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5Occupations();

    @Query("SELECT DISTINCT u.company FROM User u WHERE u.company IS NOT NULL")
    List<String> findCompanies();

    // 회사 랭킹 Top5(수정)
    @Query(value = "SELECT company, COUNT(company) AS count " +
            "FROM user " +
            "GROUP BY company " +
            "ORDER BY count DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5Companies();

    @Query("SELECT DISTINCT u.wish FROM User u WHERE u.wish IS NOT NULL")
    List<String> findWishes();

    // 희망직종 랭킹 Top5(수정)
    @Query(value = "SELECT wish, COUNT(wish) AS count " +
            "FROM user " +
            "GROUP BY wish " +
            "ORDER BY count DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5Wishes();

    // 키워드 검색을 위한 그룹 + 페이징
    @Query("SELECT u, COUNT(re) FROM User u LEFT JOIN u.resumeEdits re WHERE "
            + "(:group = 'username' AND u.username LIKE %:keyword%) OR "
            + "(:group = 'email' AND u.email LIKE %:keyword%) OR "
            + "(:group = 'company' AND u.company LIKE %:keyword%) OR "
            + "(:group = 'occupation' AND u.occupation LIKE %:keyword%) OR "
            + "(:group = 'wish' AND u.wish LIKE %:keyword%) "
            + "GROUP BY u ORDER BY u.inDate DESC")
    Page<Object[]> findByGroupAndKeyword(@Param("group") String group, @Param("keyword") String keyword, Pageable pageable);

    // ROLE_USER 조회
    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findByRole(@Param("role") String role);
    List<User> findByGender(char gender);
    List<User> findByAgeBetween(int startAge, int endAge);
    List<User> findByStatus(int status);
    List<User> findByMode(int mode);
    List<User> findByWish(String wish);
    List<User> findByOccupation(String occupation);
    List<User> findByCompany(String company);

    List<User> findByInDateBetween(Date startDate, Date endDate);
}


