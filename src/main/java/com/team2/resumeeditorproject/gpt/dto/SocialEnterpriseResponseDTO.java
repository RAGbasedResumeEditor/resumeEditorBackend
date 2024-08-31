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
public class SocialEnterpriseResponseDTO {
    private int currentCount;
    private int matchCount;
    private int page;
    private int perPage;
    private int totalCount;
    private List<SocialEnterpriseDataDTO> data;
    private String status;

}