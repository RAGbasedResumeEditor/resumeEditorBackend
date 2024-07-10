package com.team2.resumeeditorproject.statistics.dto.request;

import com.team2.resumeeditorproject.common.util.DateRange;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class DailyStatisticsRequest {
    @DateTimeFormat(pattern = "yy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yy-MM-dd")
    private LocalDate endDate;

    public DateRange toDateRange() {
        return DateRange.of(startDate, endDate);
    }
}
