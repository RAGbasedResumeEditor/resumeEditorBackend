package com.team2.resumeeditorproject.gpt.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GPTResumeGuideResponseDTO {
    private String result;
    private String status;
}
