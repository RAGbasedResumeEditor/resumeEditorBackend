
package com.team2.resumeeditorproject.resume.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name="occupation")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Occupation {
    @Id
    @Column(nullable = false, name = "occupation")
    private String occupation;
}
