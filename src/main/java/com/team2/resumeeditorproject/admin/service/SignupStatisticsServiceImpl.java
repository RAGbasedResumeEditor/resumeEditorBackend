package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.dto.response.DailySignupStatisticsResponse;
import com.team2.resumeeditorproject.admin.dto.response.MonthlySignupStatisticsResponse;
import com.team2.resumeeditorproject.admin.repository.AdminUserRepository;
import com.team2.resumeeditorproject.common.util.DateRange;
import com.team2.resumeeditorproject.common.util.DateUtils;
import com.team2.resumeeditorproject.common.util.MonthRange;
import com.team2.resumeeditorproject.user.domain.User;
import lombok.RequiredArgsConstructor;
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
public class SignupStatisticsServiceImpl implements SignupStatisticsService {

    private final AdminUserRepository userRepository;

    // 일별 회원가입 집계
    @Override
    public DailySignupStatisticsResponse getDailySignupStatistics(DateRange dateRange) {
        Map<LocalDate, Integer> signupData = new HashMap<>();
        List<LocalDate> dates = dateRange.getDates();
        try{
            List<User> users = userRepository.findByInDateBetween(DateUtils.toSqlDate(dateRange.startDate()), DateUtils.toSqlDate(dateRange.endDate()));

            for (User user : users) {
                LocalDate registrationDate = user.getInDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                signupData.merge(registrationDate, 1, Integer::sum);
            }

            dates.forEach(date -> signupData.putIfAbsent(date, 0));

            Map<LocalDate, Integer> sortedDailySignupData = new TreeMap<>(signupData);
            return new DailySignupStatisticsResponse(sortedDailySignupData);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to fetch daily signup statistics", exception);
        }
    }

    // 월별 회원가입 집계
    @Override
    public MonthlySignupStatisticsResponse getMonthlySignupStatistics(MonthRange monthRange) {
        Map<YearMonth, Integer> monthlySignupData = new HashMap<>();
        try {
            List<YearMonth> months = monthRange.getMonths();
            LocalDate startDate = monthRange.startMonth().atDay(1); // 시작 월의 첫 날
            LocalDate endDate = monthRange.endMonth().atEndOfMonth(); // 종료 월의 마지막 날

            // 유틸리티 메서드를 사용하여 LocalDate를 java.sql.Date로 변환합니다.
            List<User> users = userRepository.findByInDateBetween(DateUtils.toSqlDate(startDate), DateUtils.toSqlDate(endDate));

            // 월별 가입자 수 집계
            for (User user : users) {
                YearMonth yearMonth = YearMonth.from(user.getInDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                monthlySignupData.merge(yearMonth, 1, Integer::sum);
            }

            // 모든 월에 대해 가입자가 0인 경우에도 Map에 추가
            for (YearMonth yearMonth : months) {
                monthlySignupData.putIfAbsent(yearMonth, 0);
            }

            // 월별 가입자 수 통계를 정렬하여 `MonthlySignupStatisticsResponse` 반환
            Map<YearMonth, Integer> sortedMonthlySignupData = new TreeMap<>(monthlySignupData);
            return new MonthlySignupStatisticsResponse(sortedMonthlySignupData);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to fetch monthly access statistics", exception);
        }
    }
}
