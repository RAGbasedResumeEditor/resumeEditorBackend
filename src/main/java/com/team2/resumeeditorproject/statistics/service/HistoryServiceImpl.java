package com.team2.resumeeditorproject.statistics.service;

import com.team2.resumeeditorproject.statistics.domain.DailyStatistics;
import com.team2.resumeeditorproject.statistics.dto.HistoryDTO;
import com.team2.resumeeditorproject.statistics.repository.StatisticsHistoryRepository;
import com.team2.resumeeditorproject.statistics.repository.DailyStatisticsRepository;
import com.team2.resumeeditorproject.statistics.domain.StatisticsHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistoryServiceImpl implements HistoryService{

    private final StatisticsHistoryRepository statisticsHistoryRepository;
    private final DailyStatisticsRepository dailyStatisticsRepository;
    private final ModelMapper modelMapper;

    private final StatisticsCollectionService statisticsCollectionService;

    // 수집한 통계 저장
    @Override
    @Transactional
    public void collectStatistics() {
        HistoryDTO historyDTO = new HistoryDTO();
        log.info("Starting collectStatistics");
        try {
            // 어제 날짜 가져오기
            LocalDate yesterday = LocalDate.now().minusDays(1);
            DailyStatistics yesterdayDailyStatistics = dailyStatisticsRepository.findByReferenceDate(yesterday);

            if (yesterdayDailyStatistics != null) {
                historyDTO.setTraffic(yesterdayDailyStatistics.getVisitCount());
                historyDTO.setEdit_count(yesterdayDailyStatistics.getEditCount());
                historyDTO.setTraffic_date(yesterdayDailyStatistics.getReferenceDate());
            }

            // 통계 데이터를 수집하여 JSON 형태로 변환
            historyDTO.setUser_mode(statisticsCollectionService.getUserCountByMode());
            historyDTO.setUser_status(statisticsCollectionService.getUserCountByStatus());
            historyDTO.setUser_gender(statisticsCollectionService.getUserCountByGender());
            historyDTO.setUser_age(statisticsCollectionService.getUserCountByAge());
            historyDTO.setUser_occu(statisticsCollectionService.getTopOccupationRanksByUsers());
            historyDTO.setUser_comp(statisticsCollectionService.getTopCompanyRanksByUsers());
            historyDTO.setUser_wish(statisticsCollectionService.getTopWishRanksByUsers());
            historyDTO.setEdit_mode(statisticsCollectionService.getResumeEditCountByMode());
            historyDTO.setEdit_status(statisticsCollectionService.getResumeEditCountByStatus());
            historyDTO.setEdit_age(statisticsCollectionService.getResumeEditCountByAge());
            historyDTO.setEdit_occu(statisticsCollectionService.getTopOccupationRanksByResumeEdits());
            historyDTO.setEdit_comp(statisticsCollectionService.getTopCompanyRanksByResumeEdits());
            historyDTO.setW_date(new java.util.Date());

            // HistoryDTO를 History로 변환
           StatisticsHistory history = modelMapper.map(historyDTO, StatisticsHistory.class);

            // 데이터 저장
            statisticsHistoryRepository.save(history);

            log.debug("Completed collectStatistics successfully for date: {}", historyDTO.getTraffic_date());

        } catch (Exception exception) {
            log.error("Error occurred while collecting statistics : {}", exception.getMessage(), exception);
        }
    }

}
