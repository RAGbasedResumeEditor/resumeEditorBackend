package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.user.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;


public interface UserManagementService {

    Page<User> getAllUsersByRolePaged(String role, Pageable pageable);

    int getResumeEditCountByRNum(Long uNum);

    Page<User> searchUsersByGroupAndKeyword(String group, String keyword, String role, Pageable pageable);

    void updateUserDeleteDate(Long uNum);

    @Transactional
    void deleteUserEnd();
}
