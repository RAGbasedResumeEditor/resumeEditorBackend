package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.user.domain.User;

import java.util.List;

public interface UserManagementService {
    List<User> getAllUsersByRole(String role);
    int getResumeEditCountByRNum(Long uNum);
}
