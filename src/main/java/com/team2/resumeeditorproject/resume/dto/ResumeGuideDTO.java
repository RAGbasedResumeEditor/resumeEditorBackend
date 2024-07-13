package com.team2.resumeeditorproject.resume.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeGuideDTO {
    private Long resumeGuideNo;
    private Long userNo;
    private String company;
    private String occupation;
    private String content;
}
