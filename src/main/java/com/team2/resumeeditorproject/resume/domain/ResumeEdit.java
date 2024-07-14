package com.team2.resumeeditorproject.resume.domain;

import com.team2.resumeeditorproject.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;

/**
 * resumeEdit entity
 *
 * @author : 안은비
 * @fileName : ResumeEdit
 * @since : 04/25/24
 */

@Entity
@Getter
public class ResumeEdit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeEditNo;

    private String options;
    @Column(length = 5000)
    private String content;
    private int mode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_no")
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_no")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "occupation_no")
    private Occupation occupation;
}
