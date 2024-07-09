package com.team2.resumeeditorproject.resume.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * resume entity
 *
 * @author : 안은비
 * @fileName : Resume
 * @since : 04/26/24
 */
@Setter
@Getter
@Entity
@Table(name = "resume")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Resume {
    @Id
    private Long r_num;

    private String content;
    private Date w_date;
    private Long u_num;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "r_num", referencedColumnName = "r_num")
    private ResumeEdit resumeEdit;
}