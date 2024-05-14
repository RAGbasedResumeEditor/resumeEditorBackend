package com.team2.resumeeditorproject.comment.domain;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;


@Setter
@Getter
@Entity
@Table(name="comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "c_num")
    private Long CNum;
    @Column(nullable = false, name = "c_content")
    private String CContent;
    @Column(nullable = false, name = "r_num")
    private Long RNum;
    @Column(nullable = false, name = "u_num")
    private Long UNum;
    private Date w_date;
    private Date updated_at;
    private Date deleted_at;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "r_num")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private ResumeBoard resumeBoard;
}