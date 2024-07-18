package com.team2.resumeeditorproject.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class ErrorResponse {
    private String message;
    private Date time;
}
