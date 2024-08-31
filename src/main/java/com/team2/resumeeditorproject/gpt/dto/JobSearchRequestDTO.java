package com.team2.resumeeditorproject.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobSearchRequestDTO {
    private String salTp; // Salary Type
    private Integer minPay; // Minimum Pay
    private Integer maxPay; // Maximum Pay
    private String career; // Career Level
    private String keyword; // Search Keyword
    private String education; // Education Level
}