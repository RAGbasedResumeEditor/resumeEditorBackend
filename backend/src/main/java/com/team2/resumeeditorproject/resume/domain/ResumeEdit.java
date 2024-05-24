package com.team2.resumeeditorproject.resume.domain;


import com.fasterxml.jackson.databind.JsonNode;
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
    private Long r_num;
    private String company;
    private String occupation;
    private String item;
    private String options;
    private String r_content;
    private int mode;
    private Long u_num;
}
