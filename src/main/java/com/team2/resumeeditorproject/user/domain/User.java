package com.team2.resumeeditorproject.user.domain;

import com.team2.resumeeditorproject.resume.domain.Company;
import com.team2.resumeeditorproject.resume.domain.Occupation;
import com.team2.resumeeditorproject.resume.domain.Resume;
import com.team2.resumeeditorproject.resume.domain.ResumeEdit;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @Column(length = 50, nullable = false)
    private String email;
    @Column(length = 50, nullable = false)
    private String username;
    @Setter
    @Column(length = 100, nullable = false)
    private String password;
    @Setter
    @Column(length = 30, nullable = false)
    private String role;
    @Setter
    private int age;
    @Column(length = 20, nullable = false)
    @Setter
    private String birthDate;
    @Setter
    private char gender;
    @Setter
    private int status;
    private int mode;
    private Date createdDate;
    @Setter
    private Date deletedDate;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<ResumeEdit> resumeEdits;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<Resume> resumes;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "occupation_no", foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Occupation occupation;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_no", foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Company company;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wish_company_no", foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Company wishCompany;
}
