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
public class JobSearchResponseDTO {
    private String status; // Status of the response (e.g., Success or Fail)
    private List<JobSearchResultDTO> result;// The result data, could be a list of jobs or an error message
}