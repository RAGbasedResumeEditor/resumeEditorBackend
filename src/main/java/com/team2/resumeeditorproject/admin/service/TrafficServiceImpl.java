package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.domain.Traffic;
import com.team2.resumeeditorproject.admin.dto.TrafficDTO;
import com.team2.resumeeditorproject.admin.repository.AdminResumeRepository;
import com.team2.resumeeditorproject.admin.repository.TrafficRepository;
import com.team2.resumeeditorproject.common.util.DateRange;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TrafficServiceImpl implements TrafficService{

    private final TrafficRepository trafficRepository;
    private final AdminResumeRepository resumeRepository;
    private final ModelMapper modelMapper;
    private DateRange dateRange;

    // 트래픽 저장
    @Override
    public void saveTraffic(TrafficDTO trafficDTO) {
        Traffic traffic = modelMapper.map(trafficDTO, Traffic.class);
        traffic.setInDate(LocalDate.now().minusDays(1)); // 어제 날짜로 설정
        trafficRepository.save(traffic);
    }

    // 현재 날짜에 저장
    @Override
    public Traffic updateTrafficForToday(LocalDate date) {
        return trafficRepository.findByInDate(date);
    }

    // 총 방문자 수
    @Override
    public long getTotalVisitCount() {
        return trafficRepository.sumAllTraffic();
    }

    // 오늘 방문자 수
    @Override
    public long getVisitCountForToday() {
        Traffic todayTraffic = trafficRepository.findByInDate(LocalDate.now());
        return todayTraffic != null ? todayTraffic.getVisitCount() : 0;
    }

    // 오늘 첨삭 수 저장
    @Override
    public void saveEditCountForToday() {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // 당일 첨삭 수
        int editCount = resumeRepository.findRNumByCurrentDate(currentDate);

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
