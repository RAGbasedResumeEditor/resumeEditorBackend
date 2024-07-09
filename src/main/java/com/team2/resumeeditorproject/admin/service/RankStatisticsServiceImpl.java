package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminResumeEditRepository;
import com.team2.resumeeditorproject.admin.repository.AdminUserRepository;
import com.team2.resumeeditorproject.exception.StatisticsException;
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

    private final AdminResumeEditRepository adminResumeEditRepository;
    private final AdminUserRepository adminUserRepository;

    public static final String FAILED_TO_FETCH_TOP5_DATA = "Failed to fetch Top5 data for statistics";

    @Override
    public Map<String, Integer> getTopOccupationRanksByUsers() {
        try {
            List<Object[]> occupationCounts = adminUserRepository.findTop5Occupations();
            return occupationCounts.stream()
                    .collect(Collectors.toMap(
                            obj -> {
                                String occupation = (String) obj[0];
                                return (occupation == null || occupation.isEmpty()) ? "무직" : occupation;
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
            List<Object[]> companyCounts = adminUserRepository.findTop5Companies();
            return companyCounts.stream()
                    .collect(Collectors.toMap(
                            obj -> {
                                String company = (String) obj[0];
                                return (company == null || company.isEmpty()) ? "무직" : company;
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
            List<Object[]> wishCounts = adminUserRepository.findTop5Wishes();
            return wishCounts.stream()
                    .collect(Collectors.toMap(
                            obj -> {
                                String wish = (String) obj[0];
                                return (wish == null || wish.isEmpty()) ? "없음" : wish;
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
            List<Object[]> occupationCounts = adminResumeEditRepository.findTop5Occupations();
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
            List<Object[]> companyCounts = adminResumeEditRepository.findTop5Companies();
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
