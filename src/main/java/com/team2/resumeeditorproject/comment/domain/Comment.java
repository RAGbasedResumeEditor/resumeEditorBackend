package com.team2.resumeeditorproject.comment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.team2.resumeeditorproject.resume.domain.Resume;
import com.team2.resumeeditorproject.user.domain.User;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentNo;
    @Column(length = 1000)
    private String content;
    private Date createdDate;
    private Date updatedDate;
    private Date deletedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no") 
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_no")
    private Resume resume;

}
