package com.team2.resumeeditorproject.resume.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeGuideDTO {
    @JsonProperty("g_num")
    private Long gNum;

    @JsonProperty("u_num")
    private Long uNum;

    private String company;
    private String occupation;
    private String content;
}
