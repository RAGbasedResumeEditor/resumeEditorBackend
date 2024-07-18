package com.team2.resumeeditorproject.statistics.service;

import com.team2.resumeeditorproject.statistics.domain.DailyStatistics;
import com.team2.resumeeditorproject.statistics.repository.DailyStatisticsRepository;
import com.team2.resumeeditorproject.statistics.repository.ResumeStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TrafficServiceImpl implements TrafficService{

    private final DailyStatisticsRepository dailyStatisticsRepository;
    private final ResumeStatisticsRepository resumeStatisticsRepository;

    // 오늘 첨삭 수 저장
    @Override
    public void saveEditCountForToday() {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // 당일 첨삭 수
        int editCount = resumeStatisticsRepository.findResumeCountByCurrentDate(currentDate);

        LocalDate today = LocalDate.now();
        DailyStatistics dailyStatistics = dailyStatisticsRepository.findByReferenceDate(today);

        // 당일 데이터가 이미 존재하는 경우(로그인에 의해 이미 존재할 것임)
        if (dailyStatistics != null) {
            dailyStatistics.setEditCount(editCount);
            dailyStatisticsRepository.save(dailyStatistics);
        } else {
            dailyStatistics = new DailyStatistics();
            dailyStatistics.setReferenceDate(today);
            dailyStatistics.setVisitCount(0);
            dailyStatistics.setEditCount(editCount);
            dailyStatisticsRepository.save(dailyStatistics);
        }
    }
}
