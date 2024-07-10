package com.team2.resumeeditorproject.review.domain;

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

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "review")
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
    private String display;
    private Date w_date;
}
