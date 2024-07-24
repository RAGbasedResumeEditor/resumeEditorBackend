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
import jakarta.persistence.OneToOne;
import lombok.Getter;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

/**
 * resume entity
 *
 * @author : 안은비
 * @fileName : Resume
 * @since : 04/26/24
 */
@Entity
@Getter
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeNo;
    @Column(length = 5000, nullable = false)
    private String content;

    @Column(nullable = false)
    @CreationTimestamp
    private Date createdDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_no", foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ResumeEdit resumeEdit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private User user;
}
