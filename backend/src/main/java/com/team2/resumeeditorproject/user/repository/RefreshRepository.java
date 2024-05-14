package com.team2.resumeeditorproject.user.repository;

import com.team2.resumeeditorproject.user.domain.Refresh;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RefreshRepository extends JpaRepository<Refresh, Long> {
    Boolean existsByRefresh(String refresh);

    @Transactional
    void deleteByRefresh(String refresh);

    // user테이블에 del_date가 있다면 refresh 테이블에서 해당 회원 정보 삭제
    @Transactional
    @Modifying
    @Query("DELETE FROM Refresh WHERE username IN (SELECT u.username FROM User u WHERE u.delDate IS NOT NULL)")
    void deleteRefreshByUsernameWithDelDate();
}
