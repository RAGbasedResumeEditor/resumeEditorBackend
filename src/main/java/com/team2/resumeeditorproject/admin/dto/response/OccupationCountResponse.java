package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OccupationCountResponse {
    private String occupation;
    private int count;
}
