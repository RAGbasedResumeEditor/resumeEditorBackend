package com.team2.resumeeditorproject.user.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long uNum;
    private String email;
    private String password;
    private String name;
    private int age;
    private char gender;
    private String nickname;
    private String company;
    private String occupation;
    private String wish;
    private int status;
    private int mode;
    private Date indate;
    private Date deldate;
}
