package com.team2.resumeeditorproject.user.repository;

import com.team2.resumeeditorproject.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    User findByUsername(String username);

    /* eunbi */
    @Modifying
    @Query("UPDATE User u SET u.mode = 2 WHERE u.uNum = :u_num")
    int updateUserMode(@Param("u_num") long u_num);

    @Query("SELECT u.username FROM User u WHERE u.uNum = :uNum")
    String findUsernameByUNum(@Param("uNum") Long uNum);
}


