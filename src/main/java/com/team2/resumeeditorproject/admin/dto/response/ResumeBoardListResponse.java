package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class ResumeBoardListResponse {
    private long rNum;
    private float rating;
    private String title;
    private int ratingCount;
    private String username;
    private Date wDate;
}
