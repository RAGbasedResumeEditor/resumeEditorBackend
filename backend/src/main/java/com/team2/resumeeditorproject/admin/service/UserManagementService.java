package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface UserManagementService {

    Page<User> getAllUsersPaged(Pageable pageable);

    int getResumeEditCountByRNum(Long uNum);

    Page<User> searchUsersByGroupAndKeyword(String group, String keyword, Pageable pageable);

    void updateUserDeleteDate(Long uNum);

    void updateDelDateForRoleBlacklist();

    /*
    @Transactional
    void deleteUserEnd();
     */
}
