package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.dto.HistoryDTO;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

public interface HistoryService {
    void collectStatistics();
    //void saveStatistics(HistoryDTO historyDTO);

    Map<String, Object> getTotalTraffic();
    Map<String, Object> getProUserCnt();
    Map<String, Object> getTrafficForCurrentDate();
    Map<LocalDate, Integer> getDailyUserRegistrations(LocalDate startDate, LocalDate endDate);
    Map<LocalDate, Integer> getMonthlyUserRegistrations(YearMonth yearMonth);
    Map<String, Object> getTotalEdit();
    Map<String, Object> getRNumForCurrentDate();

    Map<String, Object> getTotalBoardCnt();

    Map<String, Object> getEditByMonthly();
    Map<String, Object> getEditByWeekly(String month);
    Map<String, Object> getEditByDaily(String startDate, String endDate);
}
