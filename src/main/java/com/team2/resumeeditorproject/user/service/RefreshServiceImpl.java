package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.user.repository.RefreshRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshServiceImpl implements RefreshService{

    private final RefreshRepository refreshRepository;

    @Override
    public void deleteExpiredTokens() {
        refreshRepository.deleteExpiredTokens();
    }

    @Override
    public void deleteRefreshByUsername(String username) {
        refreshRepository.deleteRefreshByUsername(username);
    }
}
