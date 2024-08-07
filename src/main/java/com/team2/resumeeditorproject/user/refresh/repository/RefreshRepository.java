package com.team2.resumeeditorproject.user.refresh.repository;

import com.team2.resumeeditorproject.user.refresh.domain.Refresh;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshRepository extends JpaRepository<Refresh, Long> {
    Boolean existsByRefresh(String refresh);

    @Transactional
    void deleteByRefresh(String refresh);

    // user테이블에 del_date가 있다면 refresh 테이블에서 해당 회원 정보 삭제
    @Transactional
    @Modifying
    @Query("DELETE FROM Refresh WHERE username = :username")
    void deleteRefreshByUsername(@Param("username") String username);

    // 만료 토큰 삭제
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM refresh WHERE STR_TO_DATE(expiration, '%Y-%m-%d %H:%i:%s') <= NOW() LIMIT 100", nativeQuery = true)
    void deleteExpiredTokens();
}
