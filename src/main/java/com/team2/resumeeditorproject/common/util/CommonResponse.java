package com.team2.resumeeditorproject.common.util;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class CommonResponse {
    private String response;
    private String status;
    private Date time;
}
