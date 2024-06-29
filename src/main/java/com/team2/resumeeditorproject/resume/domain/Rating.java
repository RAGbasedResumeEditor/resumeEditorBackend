package com.team2.resumeeditorproject.resume.domain;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@IdClass(RatingId.class)
@Table(name="rating")
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
