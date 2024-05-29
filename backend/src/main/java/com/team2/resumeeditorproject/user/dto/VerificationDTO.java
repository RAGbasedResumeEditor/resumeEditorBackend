package com.team2.resumeeditorproject.user.dto;

import lombok.Data;

import java.util.Date;

@Data
public class VerificationDTO {
    private Long vNum;
    private String email;
    private String code;
    private Date createdAt;
    private Date expiresAt;
}


