package com.team2.resumeeditorproject.user.repository;

import com.team2.resumeeditorproject.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    /* eunbi */
    @Modifying
    @Query("UPDATE User u SET u.mode = 2 WHERE u.userNo = :userNo")
    int updateUserMode(@Param("userNo") long userNo);

    @Query("SELECT u.username FROM User u WHERE u.userNo = :userNo")
    String findUsernameByUserNo(@Param("userNo") Long userNo);

    void deleteByDeletedDateLessThanEqual(LocalDateTime localDateTime);

}
