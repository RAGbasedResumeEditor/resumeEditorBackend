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

    @Builder
    public User(Long uNum, String email, String username, String password, String role, int age, String birthDate, char gender, String company, String occupation, String wish, int status, int mode, Date inDate, Date delDate) {
        this.uNum = uNum;
        this.email = email;
        this.username=username;
        this.password = password;
        this.role=role;
        this.age = age;
        this.birthDate=birthDate;
        this.gender = gender;
        this.company = company;
        this.occupation = occupation;
        this.wish = wish;
        this.status = status;
        this.mode = mode;
        this.inDate = inDate;
        this.delDate = delDate;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthdDte() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
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

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getDelDate() {
        return delDate;
    }

    public void setDelDate(Date delDate) {
        this.delDate = delDate;
    }
}