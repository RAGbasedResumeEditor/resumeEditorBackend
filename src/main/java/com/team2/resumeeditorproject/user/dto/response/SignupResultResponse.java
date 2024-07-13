package com.team2.resumeeditorproject.user.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class SignupResultResponse {
    private String response;
    private String status;
    private Date time;
}
