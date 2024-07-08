package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminResumeBoardRepository;
import com.team2.resumeeditorproject.admin.repository.AdminResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ResumeStatisticsServiceImpl implements ResumeStatisticsService {

    private final AdminResumeRepository adminResumeRepository;
    private final AdminResumeBoardRepository adminResumeBoardRepository;

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
}
