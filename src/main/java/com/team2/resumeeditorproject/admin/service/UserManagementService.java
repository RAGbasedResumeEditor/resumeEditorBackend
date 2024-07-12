package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.user.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserManagementService {
    Page<UserDTO> getUserList(Pageable pageable);
    Page<UserDTO> searchUsersByGroupAndKeyword(String group, String keyword, Pageable pageable);
    void updateUserDeleteDate(Long uNum);
    void updateDelDateForRoleBlacklist();

}
