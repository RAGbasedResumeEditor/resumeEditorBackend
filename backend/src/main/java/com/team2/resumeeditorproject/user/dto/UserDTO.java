package com.team2.resumeeditorproject.user.dto;

import lombok.Data;
import lombok.Setter;
import java.util.Date;

@Data
public class UserDTO {
    private Long uNum;
    private String email;
    private String username;
    private String password;
    private String role;
    private int age;
    private String birthDate;
    private char gender;
    private String company;
    private String occupation;
    private String wish;
    private int status;
    private int mode;
    private Date inDate;
    private Date delDate;
    private String authCode;
    // 스네이크로 바꿔보고 안되면?
    // 첨삭횟수 설정 메서드
    @Setter
    private int resumeEditCount; // 첨삭횟수 필드 추가
}