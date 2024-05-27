package com.team2.resumeeditorproject.resume.domain;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name="bookmark")
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
