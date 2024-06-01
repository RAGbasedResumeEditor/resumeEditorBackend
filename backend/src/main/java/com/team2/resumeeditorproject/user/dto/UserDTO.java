package com.team2.resumeeditorproject.user.dto;

import com.team2.resumeeditorproject.user.domain.Login;
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

    private Login login;
    // 스네이크로 바꿔보고 안되면?
    // 첨삭횟수 설정 메서드
    @Setter
    private int resumeEditCount; // 첨삭횟수 필드 추가
}