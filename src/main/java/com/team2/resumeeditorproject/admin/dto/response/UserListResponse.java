package com.team2.resumeeditorproject.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserListResponse {
    @JsonProperty("user_list")
    private List<UserDTO> userPage;
    @JsonProperty("total_page")
    private int totalPage;
}
