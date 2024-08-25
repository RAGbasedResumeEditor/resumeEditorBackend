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
public class GPTResumeEditResponseDTO {
    private String status;
    private List<DiffItem> diff;
    private String result;
    private String error;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiffItem {
    private int type;
    private String text;
}