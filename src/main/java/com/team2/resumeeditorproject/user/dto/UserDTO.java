package com.team2.resumeeditorproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userNo;
    private String email;
    private String username;
    private String password;
    private String role;
    private Integer age;
    private String birthDate;
    private Character gender;
    private Long companyNo;
    private String companyName;
    private Long occupationNo;
    private String occupationName;
    private Long wishCompanyNo;
    private String wishCompanyName;
    private Integer status;
    private Integer mode;
    private LocalDateTime createdDate;
    private LocalDateTime deletedDate;
    private String authCode;

    // 첨삭횟수 설정 메서드
    private int resumeEditCount; // 첨삭횟수 필드 추가

}
