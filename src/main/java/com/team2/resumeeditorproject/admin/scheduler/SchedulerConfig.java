package com.team2.resumeeditorproject.admin.config;

import com.team2.resumeeditorproject.admin.dto.HistoryDTO;
import com.team2.resumeeditorproject.admin.service.HistoryService;
import com.team2.resumeeditorproject.admin.service.TrafficService;
import com.team2.resumeeditorproject.admin.service.UserManagementService;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import com.team2.resumeeditorproject.user.service.RefreshService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class SchedulerConfig {

    private final HistoryService historyService;
    private final RefreshService refreshService;
    private final UserManagementService userManagementService;
    private final UserRepository userRepository;
    private final TrafficService trafficService;

    // 첨삭수 Traffic 테이블에 저장
    @Scheduled(cron = "0 59 23 * * ?") // 매일 오후 11시 59분 0초에 실행
    public void saveEditCount() {
        try {
            // edit_count 업데이트
            trafficService.updateEditCountForToday();
        } catch (Exception e) {
            log.error("Error occurred while updating edit count", e);
        }
    }

    // 트래픽 수집 및 저장
    @Scheduled(cron = "0 0 2 * * ?") // 매일 새벽2시에 실행
    public void saveTraffic() {
        try {
            // 통계 수집
            HistoryDTO statistics = historyService.collectStatistics();

            // 수집한 통계 데이터 저장
            historyService.saveStatistics(statistics);
        } catch (Exception e) {
            log.error("Error occurred while saving traffic", e);
        }
    }

    // 만료 토큰 삭제
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredTokens() {
        try {
            refreshService.deleteExpiredTokens();
        } catch (Exception e) {
            log.error("Error occurred while deleting expired token", e);
        }
    }

    // ROLE_BLACKLIST 회원 60일 후 ROLE_USER로 변경 후 del_date null
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateRoleForBlacklist() {
        try {
            userManagementService.updateDelDateForRoleBlacklist();
        } catch (Exception e) {
            log.error("Error occurred while updating ROLE_BLACKLIST users to ROLE_USER and setting del_date to null", e);
        }
    }

    // 30일 지나면 테이블에서 해당 회원 삭제
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void deleteUserEnd(){
        try {
            userRepository.deleteByDelDateLessThanEqual((LocalDateTime.now().minusDays(30)));
        }catch(Exception e){
            log.error("Error occurred while deleting users inactive for 30 days or more", e);
        }
    }

}
