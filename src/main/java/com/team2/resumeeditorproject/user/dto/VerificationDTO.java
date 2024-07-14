package com.team2.resumeeditorproject.user.dto;

import lombok.Data;

import java.util.Date;

@Data
public class VerificationDTO {
    private Long verificationNo;
    private String email;
    private String code;
    private Date createdDate;
    private Date expireDate;
}
