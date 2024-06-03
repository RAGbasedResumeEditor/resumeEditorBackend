package com.team2.resumeeditorproject.review.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name="review")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Review {
    @Id
    @Column(nullable = false, name = "rv_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rvNum;
    @Column(nullable = false, name = "u_num")
    private Long UNum;
    private String content;
    private int rating;
    private int mode;
    @Column(name = "`show`")
    private boolean show;
    private Date w_date;
}
