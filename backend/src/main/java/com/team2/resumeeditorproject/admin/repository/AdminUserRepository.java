package com.team2.resumeeditorproject.admin.repository;

import com.team2.resumeeditorproject.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface AdminUserRepository extends JpaRepository<User, Long> {
    // ROLE_USER 조회 + 페이징
    @Query("SELECT u FROM User u WHERE u.role = :role")
    Page<User> findByRole(@Param("role") String role, Pageable pageable);

    // 키워드 검색을 위한 그룹 + 페이징
    Page<User> findByUsernameContainingAndRole(String username, String role, Pageable pageable);
    Page<User> findByEmailContainingAndRole(String email, String role, Pageable pageable);
    Page<User> findByCompanyContainingAndRole(String company, String role, Pageable pageable);
    Page<User> findByOccupationContainingAndRole(String occupation, String role, Pageable pageable);
    Page<User> findByWishContainingAndRole(String wish, String role, Pageable pageable);

    // 회원 탈퇴
    void deleteByDelDateLessThanEqual(LocalDateTime localDateTime);

    // ROLE_USER 조회
    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findByRole(@Param("role") String role);
    List<User> findByGender(char gender);
    List<User> findByAgeBetween(int startAge, int endAge);
    List<User> findByStatus(int status);
    List<User> findByMode(int mode);
    List<User> findByWish(String wish);
    List<User> findByOccupation(String occupation);
}


