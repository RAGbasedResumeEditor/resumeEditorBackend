package com.team2.resumeeditorproject.user.repository;

import com.team2.resumeeditorproject.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    User findByUsername(String username); // username으로 DB 회원 정보 조회하는 메서드
}


