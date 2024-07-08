package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminResumeBoardRepository;
import com.team2.resumeeditorproject.admin.repository.AdminResumeEditRepository;
import com.team2.resumeeditorproject.admin.repository.AdminResumeRepository;
import com.team2.resumeeditorproject.admin.repository.AdminUserRepository;
import com.team2.resumeeditorproject.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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
}
