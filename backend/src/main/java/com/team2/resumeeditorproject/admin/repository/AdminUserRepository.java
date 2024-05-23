package com.team2.resumeeditorproject.admin.repository;

import com.team2.resumeeditorproject.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Repository
public interface AdminUserRepository extends JpaRepository<User, Long> {
    // 회원 조회 + 페이징
    @Query("SELECT u FROM User u ORDER BY u.inDate DESC")
    Page<User> findAll(Pageable pageable);

    // 키워드 검색을 위한 그룹 + 페이징
    Page<User> findByUsernameContainingOrderByInDateDesc(String username, Pageable pageable);
    Page<User> findByEmailContainingOrderByInDateDesc(String email, Pageable pageable);
    Page<User> findByCompanyContainingOrderByInDateDesc(String company, Pageable pageable);
    Page<User> findByOccupationContainingOrderByInDateDesc(String occupation,Pageable pageable);
    Page<User> findByWishContainingOrderByInDateDesc(String wish, Pageable pageable);

    // 회원 탈퇴
    List<User> findByRoleAndDelDateBefore(String role, Date date);

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

}


