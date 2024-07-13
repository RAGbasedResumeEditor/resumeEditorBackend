package com.team2.resumeeditorproject.common.util;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public record MonthRange(YearMonth startMonth, YearMonth endMonth) {

    public MonthRange {
        if (startMonth == null && endMonth != null) {
            throw new IllegalArgumentException("startMonth must be selected");
        }
        if (startMonth == null) {
            startMonth = YearMonth.now().minusMonths(6); // startMonth가 null이면 현재 월로부터 6개월 전으로 설정
        }
        if (endMonth == null) {
            endMonth = YearMonth.now(); // endMonth가 null이면 현재 월로 설정
        }
        if (startMonth.isAfter(endMonth)) {
            throw new IllegalArgumentException("startMonth must be before or equal to endMonth");
        }

    }

    public static MonthRange of(YearMonth startMonth, YearMonth endMonth) {
        return new MonthRange(startMonth, endMonth);
    }

    public List<YearMonth> getMonths() {
        List<YearMonth> months = new ArrayList<>();
        YearMonth currentMonth = startMonth;
        while (!currentMonth.isAfter(endMonth)) {
            months.add(currentMonth);
            currentMonth = currentMonth.plusMonths(1);
        }
        return months;
    }
}