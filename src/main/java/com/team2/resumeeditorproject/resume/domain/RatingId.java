package com.team2.resumeeditorproject.resume.domain;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RatingId implements Serializable {
    private Long RNum;
    private Long UNum;
}