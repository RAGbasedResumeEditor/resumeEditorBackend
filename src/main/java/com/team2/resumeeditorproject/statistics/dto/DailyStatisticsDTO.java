package com.team2.resumeeditorproject.statistics.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyStatisticsDTO {
    private Long dailyStatisticsNo;
    private int visitCount;
    private int editCount;
    private LocalDate inDate;
}
