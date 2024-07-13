package com.team2.resumeeditorproject.user.service;

public interface RefreshService {
    void deleteExpiredTokens();

    // 리프레쉬 토큰 정보 삭제
    void deleteRefreshByUsername(String username);
}
