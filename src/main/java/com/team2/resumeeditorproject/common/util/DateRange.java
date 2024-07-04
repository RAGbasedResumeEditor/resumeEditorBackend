package com.team2.resumeeditorproject.common.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record DateRange(LocalDate startDate, LocalDate endDate) {

    public DateRange {
        if (startDate == null && endDate != null){
            throw new IllegalArgumentException("startDate must be select");
        }
        if (startDate == null) {
            startDate = LocalDate.now().minusDays(6); // startDate가 null이면 현재 날짜로부터 7일 전으로 설정
        }
        if (endDate == null) {
            endDate = LocalDate.now(); // endDate가 null이면 현재 날짜로 설정
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate must be before or equal to endDate");
        }
    }

    public static DateRange of(LocalDate startDate, LocalDate endDate) {
        return new DateRange(startDate, endDate);
    }

    public List<LocalDate> getDates() {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }
        return dates;
    }
}
