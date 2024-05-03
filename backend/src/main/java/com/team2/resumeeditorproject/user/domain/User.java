package com.team2.resumeeditorproject.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter // Entity에서는 @Data 사용하지 않음 -> DB와 밀접한 관계가 있으므로
@Getter
@Entity
@Table(name="User")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uNum;
    private String email;
    private String username;
    private String password;
    private String role;
    private String name;
    private int age;
    private String birthdate;
    private char gender;
    private String nickname;
    private String company;
    private String occupation;
    private String wish;
    private int status;
    private int mode;
    private Date indate;
    private Date deldate;

    @Builder
    public User(Long uNum, String email, String username, String password, String role,String name, int age, String birthdate, char gender, String nickname, String company, String occupation, String wish, int status, int mode, Date indate, Date deldate) {
        this.uNum = uNum;
        this.email = email;
        this.username=username;
        this.password = password;
        this.role=role;
        this.name = name;
        this.age = age;
        this.birthdate=birthdate;
        this.gender = gender;
        this.nickname = nickname;
        this.company = company;
        this.occupation = occupation;
        this.wish = wish;
        this.status = status;
        this.mode = mode;
        this.indate = indate;
        this.deldate = deldate;
    }

    public Long getuNum() {
        return uNum;
    }

    public void setuNum(Long uNum) {
        this.uNum = uNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public Date getIndate() {
        return indate;
    }

    public void setIndate(Date indate) {
        this.indate = indate;
    }

    public Date getDeldate() {
        return deldate;
    }

    public void setDeldate(Date deldate) {
        this.deldate = deldate;
    }
}