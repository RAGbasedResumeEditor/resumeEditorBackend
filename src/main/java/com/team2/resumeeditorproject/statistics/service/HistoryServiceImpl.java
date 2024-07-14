package com.team2.resumeeditorproject.statistics.service;

import com.team2.resumeeditorproject.statistics.dto.StatisticsHistoryDTO;
import com.team2.resumeeditorproject.statistics.domain.DailyStatistics;
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
        StatisticsHistoryDTO historyDTO = new StatisticsHistoryDTO();
        log.info("Starting collectStatistics");
        try {
            // 어제 날짜 가져오기
            LocalDate yesterday = LocalDate.now().minusDays(1);
            DailyStatistics yesterdayDailyStatistics = dailyStatisticsRepository.findByReferenceDate(yesterday);

            if (yesterdayDailyStatistics != null) {
                historyDTO.setVisitCount(yesterdayDailyStatistics.getVisitCount());
                historyDTO.setEditCount(yesterdayDailyStatistics.getEditCount());
                historyDTO.setReferenceDate(yesterdayDailyStatistics.getReferenceDate());
            }

            // 통계 데이터를 수집하여 JSON 형태로 변환
            historyDTO.setUserMode(statisticsCollectionService.getUserCountByMode());
            historyDTO.setUserStatus(statisticsCollectionService.getUserCountByStatus());
            historyDTO.setUserGender(statisticsCollectionService.getUserCountByGender());
            historyDTO.setUserAge(statisticsCollectionService.getUserCountByAge());
            historyDTO.setEditOccupation(statisticsCollectionService.getTopOccupationRanksByUsers());
            historyDTO.setUserCompany(statisticsCollectionService.getTopCompanyRanksByUsers());
            historyDTO.setUserWish(statisticsCollectionService.getTopWishRanksByUsers());
            historyDTO.setEditMode(statisticsCollectionService.getResumeEditCountByMode());
            historyDTO.setEditStatus(statisticsCollectionService.getResumeEditCountByStatus());
            historyDTO.setEditAge(statisticsCollectionService.getResumeEditCountByAge());
            historyDTO.setEditOccupation(statisticsCollectionService.getTopOccupationRanksByResumeEdits());
            historyDTO.setEditCompany(statisticsCollectionService.getTopCompanyRanksByResumeEdits());
            historyDTO.setCreatedDate(new java.util.Date());

            // HistoryDTO를 History로 변환
           StatisticsHistory history = modelMapper.map(historyDTO, StatisticsHistory.class);

            // 데이터 저장
            statisticsHistoryRepository.save(history);

            log.debug("Completed collectStatistics successfully for date: {}", historyDTO.getCreatedDate());

        } catch (Exception exception) {
            log.error("Error occurred while collecting statistics : {}", exception.getMessage(), exception);
        }
    }

}
