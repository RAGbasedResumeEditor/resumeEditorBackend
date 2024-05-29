package com.team2.resumeeditorproject.admin.config;

import com.team2.resumeeditorproject.admin.service.HistoryService;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import com.team2.resumeeditorproject.user.service.RefreshService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

    private final HistoryService historyService;
    private final RefreshService refreshService;
    private final UserRepository userRepository;

    //@Scheduled(cron = "0 40 16 * * ?") // 테스트
    @Scheduled(cron = "0 0 2 * * ?")  // 매일 새벽2시에 실행
    public void scheduleTrafficSave() {
        try {
            Map<String, Object> statistics = historyService.collectStatistics();
            historyService.saveStatistics(statistics);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 만료 토큰 삭제
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void deleteExpiredTokens() {
        try {
            refreshService.deleteExpiredTokens();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //30일 지나면 테이블에서 해당 회원 삭제
    @Scheduled(cron = "0 0 12 * * *")
    @Transactional
    public void deleteUserEnd(){
        userRepository.deleteByDelDateLessThanEqual((LocalDateTime.now().minusDays(30)));
    }
}
