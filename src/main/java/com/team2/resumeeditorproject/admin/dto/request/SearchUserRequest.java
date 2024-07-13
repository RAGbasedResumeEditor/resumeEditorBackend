package com.team2.resumeeditorproject.admin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchUserRequest {
    private String group;
    private String keyword;
    private int pageNo;
}
