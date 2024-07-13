package com.team2.resumeeditorproject.admin.dto.response;

import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ResumeBoardListResponse {
    private List<ResumeBoardDTO> resumeBoardList;
    private int totalPages;
}
