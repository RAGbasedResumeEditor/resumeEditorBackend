package com.team2.resumeeditorproject.user.domain;

import com.team2.resumeeditorproject.resume.domain.Resume;
import com.team2.resumeeditorproject.resume.domain.ResumeEdit;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;

import java.util.Date;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE user SET deleted_date = current_timestamp,  WHERE user_no = ?") // soft delete
@DynamicUpdate
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;
    private String email;
    private String username;
    @Setter
    private String password;
    @Setter
    private String role;
    @Setter
    private int age;
    @Setter
    private String birthDate;
    @Setter
    private char gender;
    @Setter
    private String company;
    @Setter
    private String occupation;
    @Setter
    private String wish;
    @Setter
    private int status;
    private int mode;
    private Date createdDate;
    @Setter
    private Date deletedDate;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private List<ResumeEdit> resumeEdits;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Resume> resumes;

    @Builder
    public User(Long userNo, String email, String username, String password, String role, int age, String birthDate, char gender, String company, String occupation, String wish, int status, int mode, Date createdDate, Date deletedDate) {
        this.userNo = userNo;
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
        this.createdDate = createdDate;
        this.deletedDate = deletedDate;
    }
}
