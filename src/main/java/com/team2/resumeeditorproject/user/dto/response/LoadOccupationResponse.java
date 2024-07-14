package com.team2.resumeeditorproject.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team2.resumeeditorproject.resume.dto.OccupationDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class LoadOccupationResponse {
    @JsonProperty("occupation_list")
    private List<OccupationDTO> occupationList;
}
