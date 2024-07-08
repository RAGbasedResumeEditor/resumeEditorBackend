package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AgeCountResponse {
    private int count20s;
    private int count30s;
    private int count40s;
    private int count50s;
    private int count60Plus;
}
