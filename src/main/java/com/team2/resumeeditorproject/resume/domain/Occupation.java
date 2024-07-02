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
@Table(name = "occupation")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Occupation {
    @Id
    @Column(nullable = false, name = "occupation")
    private String occupation;
}
