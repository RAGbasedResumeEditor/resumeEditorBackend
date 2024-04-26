package com.team2.resumeeditorproject.resume.domain;


import jakarta.persistence.*;
import lombok.*;
import java.util.Map;

/**
 * resumeEdit entity
 *
 * @author : 안은비
 * @fileName : ResumeEdit
 * @since : 04/25/24
 */

@Setter
@Getter
@Entity
@Table(name="resume_edit")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResumeEdit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rNum;
    private String company;
    private String occupation;
    @Column(columnDefinition = "json")
    private String items;
    //    private Map<String, String> items;
    private String awards;
    private String experience;
    private String options;
    private String r_content;
    private int mode;
}
