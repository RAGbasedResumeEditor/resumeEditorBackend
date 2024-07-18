package com.team2.resumeeditorproject.user.dto.response;

import com.team2.resumeeditorproject.resume.dto.OccupationDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class LoadOccupationResponse {
    private List<OccupationDTO> occupationList;
}
