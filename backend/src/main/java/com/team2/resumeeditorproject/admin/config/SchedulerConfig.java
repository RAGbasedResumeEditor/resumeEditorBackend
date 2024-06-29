package com.team2.resumeeditorproject.admin.config;

// TODO : 사용하지 않는 import는 제거
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
// TODO : 비즈니스로직이 들어간 클래스에 Config라는 명칭은 적절하지 않고 통계스케줄러, 트래픽스케줄러 같은 이름으로 변경하는 것이 좋아보임
public class SchedulerConfig {

    private final HistoryService historyService;
    private final RefreshService refreshService;
    private final UserManagementService userManagementService;
    private final UserRepository userRepository;
    private final TrafficService trafficService;

    // TODO: 사용하지 않는 의존성은 제거
    private final TrafficInterceptor trafficInterceptor;

    // TODO : 불필요한 주석은 제거
    // TODO : "0 59 23 * * ?" 같은 문자열은 상수로 관리, ENUM으로 해도 좋음
    // TODO : 메서드 명칭은 동사로 시작하고 목적에 맞는 명칭으로 ex) saveCorrectionCount
    // TODO : Exception을 잡을거면 명확하게
    // TODO : e.printStackTrace()는 log로 대체, System.out.println()을 사용하지 않는 이유와 같음
    // 첨삭수 traffic 테이블에 저장
    @Scheduled(cron = "0 59 23 * * ?") // 매일 오후 11시 59분 0초에 실행
    public void scheduleTrafficSave() {
        try {
            // edit_count 업데이트
            trafficService.updateEditCountForToday();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO : 어떤 리턴값이나 파라미터를 Map<String, Object> 으로 전달하는 것은 지양
    // TODO : 목적에 맞는 메서드명칭을 사용, 전반적으로 수정이 필요할듯 함
    // 트래픽 수집 및 저장
    @Scheduled(cron = "0 0 2 * * ?", zone = "Asia/Seoul")  // 매일 새벽2시에 실행
    public void scheduleStatisticsSave() {
        try {
            Map<String, Object> statistics = historyService.collectStatistics();
            historyService.saveStatistics(statistics);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO : 불필요한 주석은 제거 : 특히 develop, master, release와 같은 브런치들에 이런 것들이 포함되지 않도록 주의
    /*
    // 트래픽 리셋
    @Scheduled(cron = "0 0 0 * * ?")  // 매일 자정에 실행
    public void scheduleTrafficReset() {
        try {
            trafficInterceptor.resetTrafficCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     */

    // 만료 토큰 삭제
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
    public void deleteExpiredTokens() {
        try {
            refreshService.deleteExpiredTokens();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ROLE_BLACKLIST 회원 60일 후 ROLE_USER로 변경 후 del_date null
    // TODO : zone 지정여부는 일관성있게 하는 것이 좋음
    // TODO : zone을 지정하지 않았을 때 기본값이 어떻게 세팅되는지 유의
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
