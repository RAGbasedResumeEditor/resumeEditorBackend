package com.team2.resumeeditorproject.statistics.service;

import com.team2.resumeeditorproject.common.util.DateRange;
import com.team2.resumeeditorproject.common.util.DateUtils;
import com.team2.resumeeditorproject.common.util.MonthRange;
import com.team2.resumeeditorproject.statistics.repository.UserStatisticsRepository;
import com.team2.resumeeditorproject.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignupStatisticsServiceImpl implements SignupStatisticsService {

    private final UserStatisticsRepository userStatisticsRepository;

    // 일별 회원가입 집계
    @Override
    public Map<LocalDate, Integer> getDailySignupStatistics(DateRange dateRange) {
        Map<LocalDate, Integer> signupDate = new HashMap<>();
        List<LocalDate> dates = dateRange.getDates();
        try{
            List<User> users = userStatisticsRepository.findByCreatedDateBetween(DateUtils.toSqlDate(dateRange.startDate()), DateUtils.toSqlDate(dateRange.endDate()));

            for (User user : users) {
                LocalDate registrationDate = user.getCreatedDate().toLocalDate();
                signupDate.merge(registrationDate, 1, Integer::sum);
            }

            dates.forEach(date -> signupDate.putIfAbsent(date, 0));

            return new TreeMap<>(signupDate);
        } catch (Exception exception) {
            log.error("Failed to fetch daily signup statistics", exception);
            throw new RuntimeException("Failed to fetch daily signup statistics", exception);
        }
    }

    // 월별 회원가입 집계
    @Override
    public Map<YearMonth, Integer> getMonthlySignupStatistics(MonthRange monthRange) {
        Map<YearMonth, Integer> monthlySignupDate = new HashMap<>();
        try {
            List<YearMonth> months = monthRange.getMonths();
            LocalDate startDate = monthRange.startMonth().atDay(1); // 시작 월의 첫 날
            LocalDate endDate = monthRange.endMonth().atEndOfMonth(); // 종료 월의 마지막 날

            List<User> users = userStatisticsRepository.findByCreatedDateBetween(DateUtils.toSqlDate(startDate), DateUtils.toSqlDate(endDate));

            for (User user : users) {
                YearMonth yearMonth = YearMonth.from(user.getCreatedDate());
                monthlySignupDate.merge(yearMonth, 1, Integer::sum);
            }

            for (YearMonth yearMonth : months) {
                monthlySignupDate.putIfAbsent(yearMonth, 0);
            }

            return new TreeMap<>(monthlySignupDate);
        } catch (Exception exception) {
            log.error("Failed to fetch monthly access statistics", exception);
            throw new RuntimeException("Failed to fetch monthly access statistics", exception);
        }
    }
}
