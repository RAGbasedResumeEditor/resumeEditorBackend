package com.team2.resumeeditorproject.user.domain;

import com.team2.resumeeditorproject.resume.domain.Resume;
import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="User")
@SQLDelete(sql = "UPDATE user SET del_date = current_timestamp WHERE u_num = ?") // soft delete
@NoArgsConstructor
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_num")
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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)// ResumeEdit와의 양방향 관계를 설정
    private List<ResumeEdit> resumeEdits;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Resume> resumes;

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
}