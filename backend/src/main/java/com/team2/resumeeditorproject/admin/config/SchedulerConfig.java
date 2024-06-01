package com.team2.resumeeditorproject.admin.config;

import com.team2.resumeeditorproject.admin.domain.Traffic;
import com.team2.resumeeditorproject.admin.interceptor.TrafficInterceptor;
import com.team2.resumeeditorproject.admin.service.HistoryService;
import com.team2.resumeeditorproject.admin.service.TrafficService;
import com.team2.resumeeditorproject.admin.service.UserManagementService;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import com.team2.resumeeditorproject.user.service.RefreshService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

    private final HistoryService historyService;
    private final RefreshService refreshService;
    private final UserManagementService userManagementService;
    private final UserRepository userRepository;
    private final TrafficService trafficService;

    private final TrafficInterceptor trafficInterceptor;

    // 트래픽 데이터 저장
    @Scheduled(cron = "0 0 0 * * ?") // 자정
    public void scheduleTrafficSave() {
        try {
            // edit_count 업데이트
            trafficService.updateEditCountForToday();

            Traffic todayTraffic = trafficService.getTraffic(LocalDate.now());
            if (todayTraffic != null) {
                trafficService.saveTraffic(todayTraffic.getVisitCount(), todayTraffic.getEditCount());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 트래픽 수집 및 저장
    @Scheduled(cron = "0 0 2 * * ?")  // 매일 새벽2시에 실행
    public void scheduleStatisticsSave() {
        try {
            Map<String, Object> statistics = historyService.collectStatistics();
            historyService.saveStatistics(statistics);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 트래픽 리셋
    @Scheduled(cron = "0 0 0 * * ?")  // 매일 자정에 실행
    public void scheduleTrafficReset() {
        try {
            trafficInterceptor.resetTrafficCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 만료 토큰 삭제
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredTokens() {
        try {
            refreshService.deleteExpiredTokens();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ROLE_BLACKLIST 회원 60일 후 ROLE_USER로 변경 후 del_date null
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void updateRoleForBlacklist() {
        try {
            userManagementService.updateDelDateForRoleBlacklist();
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
