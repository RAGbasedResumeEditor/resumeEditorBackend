package com.team2.resumeeditorproject.admin.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class GenderCountResponse {
    private int male;
    private int female;
}
