package com.team2.resumeeditorproject.resume.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "resume_guide")
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
