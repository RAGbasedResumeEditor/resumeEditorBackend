package com.team2.resumeeditorproject.common.scheduler;

import com.team2.resumeeditorproject.admin.service.UserManagementService;
import com.team2.resumeeditorproject.common.util.CronExpressions;
import com.team2.resumeeditorproject.statistics.service.HistoryService;
import com.team2.resumeeditorproject.statistics.service.TrafficService;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import com.team2.resumeeditorproject.user.service.RefreshService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class ApplicationScheduler {

    private final HistoryService historyService;
    private final RefreshService refreshService;
    private final UserManagementService userManagementService;
    private final UserRepository userRepository;
    private final TrafficService trafficService;

    // 첨삭수 Traffic 테이블에 저장
    @Scheduled(cron = CronExpressions.DAILY_AT_23_59_CRON)
    public void saveEditCount() {
        try {
            // edit_count 업데이트
            trafficService.saveEditCountForToday();
        } catch (Exception e) {
            log.error("Error occurred while updating edit count", e);
        }
    }

    // 트래픽 수집 및 저장
    @Scheduled(cron = CronExpressions.DAILY_AT_02_00_CRON)
    public void saveTraffic() {
        log.debug("saveTraffic method is about to be executed.");
        try {
            // 수집한 통계 데이터 저장
            historyService.collectStatistics();
            log.info("Completed saveTraffic successfully");
        } catch (Exception e) {
            log.error("Error occurred while saving traffic", e);
        }
    }

    // 만료 토큰 삭제
    @Scheduled(cron = CronExpressions.DAILY_AT_MIDNIGHT_CRON)
    public void deleteExpiredTokens() {
        try {
            refreshService.deleteExpiredTokens();
        } catch (Exception e) {
            log.error("Error occurred while deleting expired token", e);
        }
    }

    // ROLE_BLACKLIST 회원 60일 후 ROLE_USER로 변경 후 del_date null
    @Scheduled(cron = CronExpressions.DAILY_AT_MIDNIGHT_CRON)
    public void updateRoleForBlacklist() {
        try {
            userManagementService.updateDelDateForRoleBlacklist();
        } catch (Exception e) {
            log.error("Error occurred while updating ROLE_BLACKLIST users to ROLE_USER and setting del_date to null", e);
        }
    }

    // 30일 지나면 테이블에서 해당 회원 삭제
    @Scheduled(cron = CronExpressions.DAILY_AT_MIDNIGHT_CRON)
    @Transactional
    public void deleteUserEnd() {
        try {
            userRepository.deleteByDelDateLessThanEqual((LocalDateTime.now().minusDays(30)));
        } catch (Exception e) {
            log.error("Error occurred while deleting users inactive for 30 days or more", e);
        }
    }

}
