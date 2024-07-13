package com.team2.resumeeditorproject.admin.dto.response;

import com.team2.resumeeditorproject.user.dto.UserDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserListResponse {
    private List<UserDTO> userPage;
    private int totalPage;
}
