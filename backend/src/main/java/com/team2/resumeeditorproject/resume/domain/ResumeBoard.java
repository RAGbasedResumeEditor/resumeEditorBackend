package com.team2.resumeeditorproject.resume.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

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
@Table(name="resume_board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class ResumeBoard {
    @Id
    private Long r_num;
    private float rating;
    private int read_num;
    private String title;
  //  @OneToOne(fetch = FetchType.LAZY)
   // @JoinColumn(name = "r_num", referencedColumnName = "r_num")
   // private Resume resume;
}