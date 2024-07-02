package com.team2.resumeeditorproject.resume.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@IdClass(RatingId.class)
@Table(name = "rating")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Rating {
    @Id
    @Column(nullable = false, name = "r_num")
    private Long RNum;

    @Id
    @Column(nullable = false, name = "u_num")
    private Long UNum;

    private float rating;
}
