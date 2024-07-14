package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.dto.request.SearchUserRequest;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import org.springframework.data.domain.Page;

public interface UserManagementService {
    Page<UserDTO> getUserList(int pageNo, int size);
    Page<UserDTO> searchUsersByGroupAndKeyword(SearchUserRequest searchUserRequest, int size);

    void updateUserDeleteDate(Long userNo);

    void updateDeletedDateForRoleBlacklist();

}
