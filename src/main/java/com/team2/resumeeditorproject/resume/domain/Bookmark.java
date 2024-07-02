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
@Table(name = "bookmark")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Bookmark {
    @Id
    @Column(nullable = false, name = "b_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long BNum;

    @Column(nullable = false, name = "r_num")
    private Long RNum;

    @Column(nullable = false, name = "u_num")
    private Long UNum;
}
