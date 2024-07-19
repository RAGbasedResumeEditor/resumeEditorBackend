package com.team2.resumeeditorproject.resume.domain;

import java.util.List;

import com.team2.resumeeditorproject.comment.domain.Comment;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

/**
 * resumeBoard entity
 *
 * @author : 안은비
 * @fileName : ResumeBoard
 * @since : 04/30/24
 */

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResumeBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeBoardNo;

    private float rating;
    private int ratingCount;
    @Setter
    private int readCount;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_no", insertable = false, updatable = false)
    private Resume resume;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_board_no")
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_board_no")
    private List<ResumeRating> resumeRatings;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_board_no")
    private List<Bookmark> bookmarks;
}
