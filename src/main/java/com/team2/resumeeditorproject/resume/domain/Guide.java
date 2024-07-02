package com.team2.resumeeditorproject.resume.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
