package com.team2.resumeeditorproject.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter // Entity에서는 @Data 사용하지 않음 -> DB와 밀접한 관계가 있으므로
@Getter
@Entity
@Table(name="User")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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