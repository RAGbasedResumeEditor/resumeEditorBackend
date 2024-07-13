package com.team2.resumeeditorproject.resume.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class ResumeStatistics {
    @Id
    private Long resumeStatisticsNo;

    private float rating;
    private int ratingCount;
    @Setter
    private int readCount;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_no", insertable = false, updatable = false)
    private Resume resume;
}
