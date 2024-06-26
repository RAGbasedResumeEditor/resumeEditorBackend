package com.team2.resumeeditorproject.resume.domain;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name="resume_guide")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResumeGuide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "g_num")
    private Long GNum;

    @Column(name = "u_num", nullable = false)
    private Long UNum;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String occupation;

    @Column(nullable = false, length = 5000)
    private String content;
}
