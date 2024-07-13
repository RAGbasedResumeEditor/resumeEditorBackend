package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class DeleteResumeResultResponse {
    private String response;
    private String status;
    private Date time;
}
