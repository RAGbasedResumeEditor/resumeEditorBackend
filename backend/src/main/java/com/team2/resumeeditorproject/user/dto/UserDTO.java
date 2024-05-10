package com.team2.resumeeditorproject.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Setter;

import java.util.Date;

@Data
public class UserDTO {
    private Long uNum;
    private String email;
    private String username;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,12}", message = "비밀번호는 8~12자 영문 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;
    private String role;
    private int age;
    private String birthDate;
    private char gender;
    private String company;
    private String occupation;
    private String wish;
    private int status;
    private int mode=1;
    private Date inDate;
    private Date delDate;
    private String authCode;

    // 첨삭횟수 설정 메서드
    @Setter
    private int resumeEditCount; // 첨삭횟수 필드 추가

}