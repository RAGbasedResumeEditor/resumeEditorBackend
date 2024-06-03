package com.team2.resumeeditorproject.admin.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="Review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rv_num;

    private Long u_num;
    private String content;
    private int rating;
    private int mode;
    private Date w_date;
    @Column(name="`show`")
    private boolean show = false;
}
