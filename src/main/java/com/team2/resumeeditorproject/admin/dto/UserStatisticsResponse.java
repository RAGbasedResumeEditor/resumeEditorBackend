package com.team2.resumeeditorproject.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserStatisticsResponse { // 클래스 분할할 예정
    private Map<String, Object> userCount;
    private Map<String, Object> genderCount;
    private Map<String, Object> ageCount;
    private Map<String, Object> statusCount;
    private Map<String, Object> modeCount;
    private Map<String, Object> occupationCount;
    private Map<String, Object> wishCount;
    private Map<String, Map<String, Integer>> occupationRank;
    private Map<String, Map<String, Integer>> companyRank;
    private Map<String, Map<String, Integer>> wishRank;
    private Map<String, Object> proUserCount;
    private Long totalVisitCount;
    private Long visitTodayCount;
}
