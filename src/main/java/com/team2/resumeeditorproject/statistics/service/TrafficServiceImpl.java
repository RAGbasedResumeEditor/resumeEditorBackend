package com.team2.resumeeditorproject.statistics.service;

import com.team2.resumeeditorproject.statistics.domain.Traffic;
import com.team2.resumeeditorproject.statistics.repository.ResumeStatisticsRepository;
import com.team2.resumeeditorproject.statistics.repository.TrafficRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TrafficServiceImpl implements TrafficService{

    private final TrafficRepository trafficRepository;
    private final ResumeStatisticsRepository resumeStatisticsRepository;

    // 오늘 첨삭 수 저장
    @Override
    public void saveEditCountForToday() {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // 당일 첨삭 수
        int editCount = resumeStatisticsRepository.findRNumByCurrentDate(currentDate);

        LocalDate today = LocalDate.now();
        Traffic traffic = trafficRepository.findByInDate(today);

        // 당일 데이터가 이미 존재하는 경우(로그인에 의해 이미 존재할 것임)
        if (traffic != null) {
            traffic.setEditCount(editCount);
            trafficRepository.save(traffic);
        } else {
            traffic = new Traffic();
            traffic.setInDate(today);
            traffic.setVisitCount(0);
            traffic.setEditCount(editCount);
            trafficRepository.save(traffic);
        }
    }
}
