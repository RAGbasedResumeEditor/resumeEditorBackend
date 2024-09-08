package com.team2.resumeeditorproject.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GPTResumeEditRequestDTO {
    private String status;
    private String company;
    private String occupation;
    private String question;
    private String answer;
    private String model;
    private int temperature;
    private String collection;
    private String mode;
    private String technique;
}