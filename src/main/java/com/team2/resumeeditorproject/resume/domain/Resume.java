package com.team2.resumeeditorproject.resume.domain;

import com.team2.resumeeditorproject.user.domain.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import java.sql.Date;
import java.util.List;

import com.team2.resumeeditorproject.comment.domain.Comment;

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
    private Long resumeNo;
    private String content;
    private Date createdDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_no") //, foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ResumeEdit resumeEdit;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_no") //, foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;
}
