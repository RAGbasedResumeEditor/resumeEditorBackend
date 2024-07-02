package com.team2.resumeeditorproject.resume.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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

@Setter
@Getter
@Entity
@Table(name = "resume_board")
@NoArgsConstructor
@AllArgsConstructor
public class ResumeBoard {
    @Id
    @Column(nullable = false, name = "r_num")
    private Long RNum;

    private float rating;
    private int rating_count;
    private int read_num;
    private String title;
}