package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WishCountResponse {
    private String wish;
    private int count;
}
