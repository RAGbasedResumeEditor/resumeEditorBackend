package com.team2.resumeeditorproject.resume.domain;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name="guide")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Guide {
    @Id
    @Column(nullable = false, name = "u_num")
    private Long UNum;
    @Column(nullable = true, name = "awards")
    private String awards;
    @Column(nullable = true, name = "experiences")
    private String experiences;
}
