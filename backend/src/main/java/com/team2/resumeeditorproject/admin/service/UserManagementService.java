package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.user.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.Map;


public interface UserManagementService {

    Page<User> getAllUsersPaged(Pageable pageable);

    int getResumeEditCountByRNum(Long uNum);

    Page<User> searchUsersByGroupAndKeyword(String group, String keyword, Pageable pageable);

    void updateUserDeleteDate(Long uNum);

    void updateDelDateForRoleBlacklist();

    /*
    @Transactional
    @Scheduled(cron = "0 0 12 * * *") // 매일 오후 12시에 메서드 동작
    void deleteUserEnd();
     */
}
