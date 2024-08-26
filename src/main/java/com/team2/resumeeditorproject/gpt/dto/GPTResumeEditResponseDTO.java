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
public class GPTResumeEditResponseDTO {
    private String status;
    private List<List<Object>> diff;
    private String result;
}
