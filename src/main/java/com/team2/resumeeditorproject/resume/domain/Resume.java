package com.team2.resumeeditorproject.resume.domain;

import com.team2.resumeeditorproject.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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
    @Column(name = "r_num")
    private Long r_num;

    private String content;
    private Date w_date;

    @Column(name = "u_num", insertable = false, updatable = false)  // u_num 컬럼을 매핑
    private Long u_num;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "r_num", referencedColumnName = "r_num")
    private ResumeEdit resumeEdit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_num" )
    private User user; // u_num 컬럼이 User와 Resume 간의 관계를 매핑

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY)
    private List<ResumeBoard> resumeBoards;
}