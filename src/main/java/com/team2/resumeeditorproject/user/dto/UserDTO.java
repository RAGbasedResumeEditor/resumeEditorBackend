package com.team2.resumeeditorproject.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserDTO {
    private Long userNo;
    private String email;
    private String username;
    private String password;
    private String role;
    private Integer age = 0;
    private String birthDate;
    private Character gender='M';
    private Long companyNo = 1L;
    private String companyName;
    private Long occupationNo = 1L;
    private String occupationName;
    private Long wishCompanyNo = 1L;
    private String wishCompanyName;
    private Integer status = 0;
    private Integer mode;
    private Date createdDate;
    private Date deletedDate;
    private String authCode;

    // 첨삭횟수 설정 메서드
    private int resumeEditCount; // 첨삭횟수 필드 추가

}
