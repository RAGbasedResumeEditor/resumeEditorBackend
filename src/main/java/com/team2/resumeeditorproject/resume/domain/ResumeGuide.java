package com.team2.resumeeditorproject.resume.domain;

import com.team2.resumeeditorproject.user.domain.User;

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
import lombok.Getter;

@Entity
@Getter
public class ResumeGuide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeGuideNo;

    @Column(nullable = false, length = 5000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_no", foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "occupation_no", foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private Occupation occupation;
}
