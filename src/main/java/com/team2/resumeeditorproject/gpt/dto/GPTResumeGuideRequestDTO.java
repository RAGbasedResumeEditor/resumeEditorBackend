package com.team2.resumeeditorproject.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GPTResumeGuideRequestDTO {
    private String company;
    private String occupation;
    private List<Map<String, String>> questions;
    private List<Map<String, String>> awards;
    private List<Map<String, String>> experiences;
}