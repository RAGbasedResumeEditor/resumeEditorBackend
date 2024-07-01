package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.domain.Traffic;
import com.team2.resumeeditorproject.admin.dto.TrafficDTO;
import com.team2.resumeeditorproject.admin.repository.AdminResumeRepository;
import com.team2.resumeeditorproject.admin.repository.TrafficRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class TrafficServiceImpl implements TrafficService{

    private final TrafficRepository trafficRepository;
    private final AdminResumeRepository resumeRepository;
    private final ModelMapper modelMapper;

    // 트래픽 저장
    @Override
    public void saveTraffic(TrafficDTO trafficDTO) {
        Traffic traffic = modelMapper.map(trafficDTO, Traffic.class);
        traffic.setInDate(LocalDate.now().minusDays(1)); // 어제 날짜로 설정
        trafficRepository.save(traffic);
    }

    // 현재 날짜에 저장
    @Override
    public Traffic getTraffic(LocalDate date) {
        return trafficRepository.findByInDate(date);
    }

    // 총 방문자 수
    @Override
    public long getTotalTraffic() {
        return trafficRepository.sumAllTraffic();
    }

    // 오늘 방문자 수
    @Override
    public long getTrafficForCurrentDate() {
        Traffic todayTraffic = trafficRepository.findByInDate(LocalDate.now());
        return todayTraffic != null ? todayTraffic.getVisitCount() : 0;
    }

    // 일별 접속자 집계
    @Override
    public Map<LocalDate, Integer> getTrafficData(LocalDate startDate, LocalDate endDate) {
        Map<LocalDate, Integer> trafficData = new HashMap<>();

        List<Traffic> trafficList = trafficRepository.findByInDateBetween(startDate, endDate);

        // 시작일부터 종료일까지의 각 날짜에 대한 트래픽 데이터 집계
        for (LocalDate date = startDate; date.isBefore(endDate) || date.isEqual(endDate); date = date.plusDays(1)) {
            final LocalDate currentDate = date;

            // 해당 날짜에 대한 방문자 수 계산
            int visitCount = trafficList.stream()
                    .filter(traffic -> traffic.getInDate().isEqual(currentDate))
                    .mapToInt(Traffic::getVisitCount)
                    .sum();
            trafficData.put(date, visitCount);
        }

        // 접속자 데이터가 없는 날짜에는 0을 설정
        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            trafficData.putIfAbsent(date, 0);
        }

        return trafficData;
    }

    // 월별 접속자 집계
    @Override
    public Map<YearMonth, Integer> getMonthlyTrafficData(LocalDate startDate, LocalDate endDate) {
        Map<YearMonth, Integer> monthlyTrafficData = new HashMap<>();

        List<Traffic> trafficList = trafficRepository.findByInDateBetween(startDate, endDate);

        // 시작일부터 종료일까지의 각 월에 대한 접속자 데이터 집계
        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            YearMonth yearMonth = YearMonth.from(date);

            // 해당 월에 대한 방문자 수 계산
            int visitCount = trafficList.stream()
                    .filter(traffic -> YearMonth.from(traffic.getInDate()).equals(yearMonth))
                    .mapToInt(Traffic::getVisitCount)
                    .sum();
            monthlyTrafficData.put(yearMonth, visitCount);
        }

        // 접속자 데이터가 없는 날짜에는 0을 설정
        YearMonth startMonth = YearMonth.from(startDate);
        YearMonth endMonth = YearMonth.from(endDate);
        for (YearMonth yearMonth = startMonth; !yearMonth.isAfter(endMonth); yearMonth = yearMonth.plusMonths(1)) {
            monthlyTrafficData.putIfAbsent(yearMonth, 0);
        }
        return new TreeMap<>(monthlyTrafficData);
    }

    // 오늘 첨삭 수 저장
    @Override
    public void updateEditCountForToday() {
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
