package com.team2.resumeeditorproject.user.repository;

import com.team2.resumeeditorproject.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
<<<<<<< HEAD
    User findByUsername(String username); // username으로 DB 회원 정보 조회하는 메서드
=======
    User findByUsername(String username);
>>>>>>> 8b97b5ac7d556187da118a8fe88d7f4326108f33
}


