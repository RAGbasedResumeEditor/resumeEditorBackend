package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminResumeBoardRepository;
import com.team2.resumeeditorproject.admin.repository.AdminResumeEditRepository;
import com.team2.resumeeditorproject.admin.repository.AdminResumeRepository;
import com.team2.resumeeditorproject.admin.repository.AdminUserRepository;
import com.team2.resumeeditorproject.common.util.DateRange;
import com.team2.resumeeditorproject.common.util.DateUtils;
import com.team2.resumeeditorproject.common.util.MonthRange;
import com.team2.resumeeditorproject.resume.domain.Resume;
import com.team2.resumeeditorproject.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeStatisticsServiceImpl implements ResumeStatisticsService {

    private final AdminResumeRepository adminResumeRepository;
    private final AdminResumeBoardRepository adminResumeBoardRepository;
    private final AdminResumeEditRepository adminResumeEditRepository;
    private final AdminUserRepository adminUserRepository;

    private int getResumeEditCount(List<User> userList) {
        List<Long> userNo = userList.stream()
                .map(User::getUNum)
                .collect(Collectors.toList());

        List<Object[]> results = adminResumeEditRepository.countByUNumIn(userNo);

        return results.stream()
                .mapToInt(result -> ((Long) result[1]).intValue())
                .sum();
    }

    @Override
    public long getTotalResumeEditCount() {
        return adminResumeRepository.count();
    }

    @Override
    public int getTodayResumeEditCount() {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return adminResumeRepository.findRNumByCurrentDate(currentDate);
    }

    @Override
    public long getTotalResumeBoardCount() {
        return adminResumeBoardRepository.count();
    }

    @Override
    public int getResumeEditCountByStatus(int status) {
        List<User> userList = adminUserRepository.findByStatus(status);
        return getResumeEditCount(userList);
    }

    @Override
    public int getResumeEditCountByAge(int startAge, int endAge) {
        List<User> userList = adminUserRepository.findByAgeBetween(startAge, endAge);
        return getResumeEditCount(userList);
    }

    @Override
    public int getResumeEditCountByMode(int mode) {
        List<User> userList = adminUserRepository.findByMode(mode);
        return getResumeEditCount(userList);
    }

    // 일별 첨삭 집계
    @Override
    public Map<LocalDate, Integer> getDailyResumeEditStatistics(DateRange dateRange) {
        Map<LocalDate, Integer> editData = new HashMap<>();
        List<LocalDate> dates = dateRange.getDates();

        Date startDate = DateUtils.toSqlDate(dateRange.startDate());
        Date endDate = DateUtils.toSqlDate(dateRange.endDate().plusDays(1)); // endDate + 1일로 설정하여 범위 포함

        try {
            List<Resume> resumeList = adminResumeRepository.findByWDateBetween(startDate, endDate);

            for (Resume resume : resumeList) {
                LocalDate date = resume.getW_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                editData.put(date, editData.getOrDefault(date, 0) + 1);
            }

            dates.forEach(date -> editData.putIfAbsent(date, 0));

            return new TreeMap<>(editData);
        } catch (Exception exception) {
            log.error("Failed to fetch daily resume edit statistics", exception);
            throw new RuntimeException("Failed to fetch daily resume edit statistics", exception);
        }
    }

    // 월별 첨삭 집계
    @Override
    public Map<YearMonth, Integer> getMonthlyResumeEditStatistics(MonthRange monthRange) {
        Map<YearMonth, Integer> monthlyEditDate = new HashMap<>();
        List<YearMonth> months = monthRange.getMonths();

        LocalDate startDate = monthRange.startMonth().atDay(1);  // 시작 월의 첫 날
        LocalDate endDate = monthRange.endMonth().atEndOfMonth();  // 종료 월의 마지막 날
        try {
            List<Resume> resumeList = adminResumeRepository.findByWDateBetween(java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate));

            for (Resume resume : resumeList) {
                LocalDate wDate = resume.getW_date().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                YearMonth yearMonth = YearMonth.from(wDate);

                monthlyEditDate.put(yearMonth, monthlyEditDate.getOrDefault(yearMonth, 0) + 1);
            }

            for (YearMonth yearMonth : months) {
                monthlyEditDate.putIfAbsent(yearMonth, 0);
            }

            return new TreeMap<>(monthlyEditDate);
        } catch (Exception exception) {
            log.error("Failed to fetch monthly resume edit statistics", exception);
            throw new RuntimeException("Failed to fetch monthly resume edit statistics", exception);
        }
    }
}
