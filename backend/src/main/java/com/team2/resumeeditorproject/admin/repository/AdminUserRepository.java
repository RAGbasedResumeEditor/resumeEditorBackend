package com.team2.resumeeditorproject.admin.repository;

import com.team2.resumeeditorproject.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminUserRepository extends JpaRepository<User, Long> {
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


