package com.team2.resumeeditorproject.resume.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    private Long RNum;
    private Long UNum;
    private float rating;
}
