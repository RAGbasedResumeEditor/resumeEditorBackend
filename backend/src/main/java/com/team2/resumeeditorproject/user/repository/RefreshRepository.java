package com.team2.resumeeditorproject.user.repository;

import com.team2.resumeeditorproject.user.domain.Refresh;
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
    @Query(value = "DELETE FROM refresh r WHERE " +
            "STR_TO_DATE(r.expiration, '%Y-%m-%d %H:%i:%s') <= NOW()", nativeQuery = true)
    void deleteExpiredTokens();
}
