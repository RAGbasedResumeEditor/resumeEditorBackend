package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.domain.Traffic;
import com.team2.resumeeditorproject.admin.repository.TrafficRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TrafficServiceImpl implements TrafficService{

    private final TrafficRepository trafficRepository;

    // 트래픽 저장
    @Override
    public void saveTraffic(int visitCount, int editCount) {
        Traffic traffic = new Traffic();
        traffic.setVisitCount(visitCount);
        traffic.setEditCount(editCount);
        traffic.setInDate(LocalDate.now());
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

        return trafficData;
    }
}
