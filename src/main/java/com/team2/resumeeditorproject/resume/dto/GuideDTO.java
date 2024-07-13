package com.team2.resumeeditorproject.resume.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuideDTO {
    private Long userNo;
    private String awards;
    private String experiences;
}
