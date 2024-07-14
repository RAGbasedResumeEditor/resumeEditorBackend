package com.team2.resumeeditorproject.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team2.resumeeditorproject.user.dto.ResumeEditDetailDTO;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResumeEditDetailResponse {
    @JsonProperty("resume_edit_detail")
    private ResumeEditDetailDTO resumeEditDetailDTO;
}
