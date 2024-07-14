package com.team2.resumeeditorproject.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ResumeBoardListResponse {
    @JsonProperty("resume_board_list")
    private List<ResumeBoardDTO> resumeBoardList;
    @JsonProperty("total_page")
    private int totalPage;
}
