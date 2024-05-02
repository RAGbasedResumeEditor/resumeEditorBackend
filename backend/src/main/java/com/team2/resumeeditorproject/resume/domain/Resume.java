package com.team2.resumeeditorproject.resume.domain;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

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
@Table(name="resume")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Resume {
    @Id
    private Long r_num;
    private String content;
    private Date wdate;
    private Long u_num;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "r_num", referencedColumnName = "r_num")
    private ResumeEdit resumeEdit;
}