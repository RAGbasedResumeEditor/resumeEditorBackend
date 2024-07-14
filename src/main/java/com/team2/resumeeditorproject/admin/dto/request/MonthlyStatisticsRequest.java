package com.team2.resumeeditorproject.admin.dto.request;

import com.team2.resumeeditorproject.common.util.MonthRange;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.YearMonth;

@Data
public class MonthlyStatisticsRequest {
    @DateTimeFormat(pattern = "yyyy-MM")
    private YearMonth startDate;

    @DateTimeFormat(pattern = "yyyy-MM")
    private YearMonth endDate;

    public MonthRange toMonthRange() {
        return MonthRange.of(startDate, endDate);
    }
}
