package com.team2.resumeeditorproject.occupation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OccupationDTO {
    private Long occupationNo;
    private String occupationName;
}
