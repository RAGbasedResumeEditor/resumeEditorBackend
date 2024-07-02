package com.team2.resumeeditorproject.resume.domain;

import com.team2.resumeeditorproject.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Table(name = "resume_edit")
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

    @Column(name = "u_num")
    private Long u_num;

    @ManyToOne
    @JoinColumn(name = "u_num", insertable = false, updatable = false) //User 엔티티와 관계 정의
    private User user;
}
