package com.team2.resumeeditorproject.comment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name="comment")
@NoArgsConstructor
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
}