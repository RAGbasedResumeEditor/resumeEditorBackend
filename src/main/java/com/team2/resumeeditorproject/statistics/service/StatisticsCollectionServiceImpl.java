package com.team2.resumeeditorproject.statistics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.resumeeditorproject.statistics.dto.response.AgeCountResponse;
import com.team2.resumeeditorproject.statistics.dto.response.GenderCountResponse;
import com.team2.resumeeditorproject.statistics.dto.response.ModeCountResponse;
import com.team2.resumeeditorproject.statistics.dto.response.RankByResumeEditResponse;
import com.team2.resumeeditorproject.statistics.dto.response.RankByUserResponse;
import com.team2.resumeeditorproject.statistics.dto.response.StatusCountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsCollectionServiceImpl implements StatisticsCollectionService {

    private final UserStatisticsService userStatisticsService;
    private final RankStatisticsService rankStatisticsService;
    private final ResumeStatisticsService resumeStatisticsService;
    private final ObjectMapper objectMapper;

    private String convertToJson(Object response) {
        try {
            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("Error converting to JSON", exception);
        }
    }

    @Override
    public String getUserCountByMode() {
        return convertToJson(ModeCountResponse.builder()
                .lightMode(userStatisticsService.getUserCountByMode(1))
                .proMode(userStatisticsService.getUserCountByMode(2))
                .build());
    }

    @Override
    public String getUserCountByStatus() {
        return convertToJson(StatusCountResponse.builder()
                .status1(userStatisticsService.getUserCountByStatus(1))
                .status2(userStatisticsService.getUserCountByStatus(2))
                .build());
    }

    @Override
    public String getUserCountByGender() {
        return convertToJson(GenderCountResponse.builder()
                .female(userStatisticsService.getUserCountByGender('F'))
                .male(userStatisticsService.getUserCountByGender('M'))
                .build());
    }

    @Override
    public String getUserCountByAge() {
        return convertToJson(AgeCountResponse.builder()
                .count20s(userStatisticsService.getUserCountByAgeGroup(20, 29))
                .count30s(userStatisticsService.getUserCountByAgeGroup(30, 39))
                .count40s(userStatisticsService.getUserCountByAgeGroup(40, 49))
                .count50s(userStatisticsService.getUserCountByAgeGroup(50, 59))
                .count60Plus(userStatisticsService.getUserCountByAgeGroup(60, Integer.MAX_VALUE))
                .build());
    }

    @Override
    public String getTopOccupationRanksByUsers() {
        return convertToJson(RankByUserResponse.builder()
                .userRanking(rankStatisticsService.getTopOccupationRanksByUsers())
                .build());
    }

    @Override
    public String getTopCompanyRanksByUsers() {
        return convertToJson(RankByUserResponse.builder()
                .userRanking(rankStatisticsService.getTopCompanyRanksByUsers())
                .build());
    }

    @Override
    public String getTopWishRanksByUsers() {
        return convertToJson(RankByUserResponse.builder()
                .userRanking(rankStatisticsService.getTopWishRanksByUsers())
                .build());
    }

    @Override
    public String getResumeEditCountByMode() {
        return convertToJson(ModeCountResponse.builder()
                .lightMode(resumeStatisticsService.getResumeEditCountByMode(1))
                .proMode(resumeStatisticsService.getResumeEditCountByMode(2))
                .build());
    }

    @Override
    public String getResumeEditCountByStatus() {
        return convertToJson(StatusCountResponse.builder()
                .status1(resumeStatisticsService.getResumeEditCountByStatus(1))
                .status2(resumeStatisticsService.getResumeEditCountByStatus(2))
                .build());
    }

    @Override
    public String getResumeEditCountByAge() {
        return convertToJson(AgeCountResponse.builder()
                .count20s(resumeStatisticsService.getResumeEditCountByAgeGroup(20, 29))
                .count30s(resumeStatisticsService.getResumeEditCountByAgeGroup(30, 39))
                .count40s(resumeStatisticsService.getResumeEditCountByAgeGroup(40, 49))
                .count50s(resumeStatisticsService.getResumeEditCountByAgeGroup(50, 59))
                .count60Plus(resumeStatisticsService.getResumeEditCountByAgeGroup(60, Integer.MAX_VALUE))
                .build());
    }

    @Override
    public String getTopOccupationRanksByResumeEdits() {
        return convertToJson(RankByResumeEditResponse.builder()
                .editRanking(rankStatisticsService.getTopOccupationRanksByResumeEdits())
                .build());

    }

    @Override
    public String getTopCompanyRanksByResumeEdits() {
        return convertToJson(RankByResumeEditResponse.builder()
                .editRanking(rankStatisticsService.getTopCompanyRanksByResumeEdits())
                .build());
    }
}
