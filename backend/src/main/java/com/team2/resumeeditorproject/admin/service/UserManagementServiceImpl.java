package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminUserRepository;
import com.team2.resumeeditorproject.admin.repository.AdminResumeEditRepository;
import com.team2.resumeeditorproject.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService{

    private final AdminUserRepository adminUserRepository;
    private final AdminResumeEditRepository adminResumeEditRepository;

    @Override
    public List<User> getAllUsersByRole(String role) {
        return adminUserRepository.findByRole(role);
    }

    @Override
    public int getResumeEditCountByRNum(Long uNum) {
        return adminResumeEditRepository.countByRNum(uNum);
    }
}
