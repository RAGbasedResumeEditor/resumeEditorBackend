package com.team2.resumeeditorproject.user.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private Long userNo;
    private String email;
    private String username;
    private String password;
    private String role;
    private Integer age;
    private String birthDate;
    private Character gender;
    private String company;
    private String occupation;
    private String wish;
    private Integer status;
    private Integer mode;
    private Date inDate;
    private Date delDate;
    private String authCode;
    // 스네이크로 바꿔보고 안되면?
    // 첨삭횟수 설정 메서드
    private int resumeEditCount; // 첨삭횟수 필드 추가

}
