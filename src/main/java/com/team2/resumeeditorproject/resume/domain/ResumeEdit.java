package com.team2.resumeeditorproject.resume.domain;

import java.util.List;

import com.team2.resumeeditorproject.company.domain.Company;
import com.team2.resumeeditorproject.occupation.domain.Occupation;
import com.team2.resumeeditorproject.user.domain.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * resumeEdit entity
 *
 * @author : 안은비
 * @fileName : ResumeEdit
 * @since : 04/25/24
 */

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResumeEdit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeEditNo;

    private String options;
    @Column(length = 5000)
    private String question;
    @Column(length = 5000)
    private String content;
    private int mode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_no", foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_no", foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "occupation_no", foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Occupation occupation;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "resume_edit_no", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<ResumeEditExtension> entityExtensionList;
}
