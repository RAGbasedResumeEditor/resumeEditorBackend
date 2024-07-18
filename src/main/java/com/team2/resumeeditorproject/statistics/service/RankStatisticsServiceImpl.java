package com.team2.resumeeditorproject.statistics.service;

import com.team2.resumeeditorproject.exception.StatisticsException;
import com.team2.resumeeditorproject.resume.repository.OccupationRepository;
import com.team2.resumeeditorproject.statistics.repository.ResumeEditStatisticsRepository;
import com.team2.resumeeditorproject.statistics.repository.UserStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RankStatisticsServiceImpl implements RankStatisticsService {

    private final ResumeEditStatisticsRepository resumeEditStatisticsRepository;
    private final UserStatisticsRepository userStatisticsRepository;
    private final OccupationRepository occupationRepository;

    public static final String FAILED_TO_FETCH_TOP5_DATA = "Failed to fetch Top5 data for statistics";

    @Override
    public Map<String, Integer> getTopOccupationRanksByUsers() {
        try {
            List<Object[]> occupationCounts = userStatisticsRepository.findTop5Occupations();

            return occupationCounts.stream()
                    .collect(Collectors.toMap(
                            obj -> {
                                String occupationName = (String) obj[0];
                                return (occupationName == null || occupationName.isEmpty()) ? "무직" : occupationName;
                            },
                            obj -> ((Long) obj[1]).intValue()
                    ));
        } catch (Exception exception) {
            log.error("Failed to fetch top 5 occupation ranks by users, {}", exception.getMessage());
            throw new StatisticsException(FAILED_TO_FETCH_TOP5_DATA);
        }
    }

    @Override
    public Map<String, Integer> getTopCompanyRanksByUsers() {
        try {
            List<Object[]> companyCounts = userStatisticsRepository.findTop5Companies();
            return companyCounts.stream()
                    .collect(Collectors.toMap(
                            obj -> {
                                String companyName = (String) obj[0];
                                return (companyName == null || companyName.isEmpty()) ? "무직" : companyName;
                            },
                            obj -> ((Long) obj[1]).intValue()
                    ));
        } catch (Exception exception) {
            log.error("Failed to fetch top 5 company ranks by users, {}", exception.getMessage());
            throw new StatisticsException(FAILED_TO_FETCH_TOP5_DATA);
        }
    }

    @Override
    public Map<String, Integer> getTopWishRanksByUsers() {
        try {
            List<Object[]> wishCounts = userStatisticsRepository.findTop5Wishes();
            return wishCounts.stream()
                    .collect(Collectors.toMap(
                            obj -> {
                                String wishCompanyName = (String) obj[0];
                                return (wishCompanyName == null || wishCompanyName.isEmpty()) ? "없음" : wishCompanyName;
                            },
                            obj -> ((Long) obj[1]).intValue()
                    ));
        } catch (Exception exception) {
            log.error("Failed to fetch top 5 wish ranks by users, {}", exception.getMessage());
            throw new StatisticsException(FAILED_TO_FETCH_TOP5_DATA);
        }
    }

    @Override
    public Map<String, Integer> getTopOccupationRanksByResumeEdits() {
        try {
            List<Object[]> occupationCounts = resumeEditStatisticsRepository.findTop5Occupations();
            return occupationCounts.stream()
                    .collect(Collectors.toMap(
                            obj -> (String) obj[0],
                            obj -> ((Long) obj[1]).intValue()
                    ));
        } catch (Exception exception) {
            log.error("Failed to fetch top 5 occupation ranks by resume edits, {}", exception.getMessage());
            throw new StatisticsException(FAILED_TO_FETCH_TOP5_DATA);
        }
    }

    @Override
    public Map<String, Integer> getTopCompanyRanksByResumeEdits() {
        try {
            List<Object[]> companyCounts = resumeEditStatisticsRepository.findTop5Companies();
            return companyCounts.stream()
                    .collect(Collectors.toMap(
                            obj -> (String) obj[0],
                            obj -> ((Long) obj[1]).intValue()
                    ));
        } catch (Exception exception) {
            log.error("Failed to fetch top 5 company ranks by resume edits, {}", exception.getMessage());
            throw new StatisticsException(FAILED_TO_FETCH_TOP5_DATA);
        }
    }
}
