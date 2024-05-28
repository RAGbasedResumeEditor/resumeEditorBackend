package com.team2.resumeeditorproject.admin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.resumeeditorproject.admin.domain.History;
import com.team2.resumeeditorproject.admin.interceptor.TrafficInterceptor;
import com.team2.resumeeditorproject.admin.repository.AdminResumeEditRepository;
import com.team2.resumeeditorproject.admin.repository.AdminUserRepository;
import com.team2.resumeeditorproject.admin.repository.HistoryRepository;
import com.team2.resumeeditorproject.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService{

    private final HistoryRepository historyRepository;
    private final AdminUserRepository userRepository;

    private final ObjectMapper objectMapper;
    private final TrafficInterceptor trafficInterceptor;
    private final AdminResumeEditRepository adminResumeEditRepository;
    private final AdminService adminService;

    /* 통계 수집 */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> collectStatistics() {
        Map<String, Object> statistics = new LinkedHashMap<>();
        statistics.put("traffic", trafficInterceptor.getTrafficCnt());
        statistics.put("edit_count", resumeEditCntByStatus());
        statistics.put("user_mode", getUserMode());
        statistics.put("user_status", getUserStatus());
        statistics.put("user_gender", getUserGender());
        statistics.put("user_age", getUserAge());

        Map<String, Map<String, Integer>> getRankOccuUser = getUserOccu();
        statistics.put("user_occu", getRankOccuUser.get("ranking_user"));

        Map<String, Map<String, Integer>> getRankCompUser = getUserComp();
        statistics.put("user_comp", getRankCompUser.get("ranking_user"));

        Map<String, Map<String, Integer>> getRankWishUser = getUserWish();
        statistics.put("user_wish", getRankWishUser.get("ranking_user"));

        Map<String, Object> getEditModeRatio = getEditMode();
        statistics.put("edit_mode", getEditModeRatio.get("edit_ratio"));

        Map<String, Object> getEditStatusRatio = getEditStatus();
        statistics.put("edit_status", getEditStatusRatio.get("edit_ratio"));

        Map<String, Object> getEditAgeRatio = getEditAge();
        statistics.put("edit_age", getEditAgeRatio.get("age_edit_ratio"));

        statistics.put("edit_date", getEditDate());

        Map<String, Map<String, Integer>> getRankOccuEdit = getEditOccu();
        statistics.put("edit_occu", getRankOccuEdit.get("ranking_resumeEdit"));

        Map<String, Map<String, Integer>> getRankCompEdit = getEditComp();
        statistics.put("edit_comp", getRankCompEdit.get("ranking_resumeEdit"));
        return statistics;
    }

    /* DB에 저장 */
    @Override
    @Transactional
    public void saveStatistics(Map<String, Object> statistics) {
        // Statistics 저장 로직
        try {
            History history = new History();
            history.setTraffic((int) statistics.get("traffic"));
            history.setEdit_count((int) statistics.get("edit_count"));
            history.setUser_mode(objectMapper.writeValueAsString(statistics.get("user_mode")));
            history.setUser_status(objectMapper.writeValueAsString(statistics.get("user_status")));
            history.setUser_gender(objectMapper.writeValueAsString(statistics.get("user_gender")));
            history.setUser_age(objectMapper.writeValueAsString(statistics.get("user_age")));
            history.setUser_occu(objectMapper.writeValueAsString(statistics.get("user_occu")));
            history.setUser_comp(objectMapper.writeValueAsString(statistics.get("user_comp")));
            history.setUser_wish(objectMapper.writeValueAsString(statistics.get("user_wish")));
            history.setEdit_mode(objectMapper.writeValueAsString(statistics.get("edit_mode")));
            history.setEdit_status(objectMapper.writeValueAsString(statistics.get("edit_status")));
            history.setEdit_age(objectMapper.writeValueAsString(statistics.get("edit_age")));
            history.setEdit_date(objectMapper.writeValueAsString(statistics.get("edit_date")));
            history.setEdit_occu(objectMapper.writeValueAsString(statistics.get("edit_occu")));
            history.setEdit_comp(objectMapper.writeValueAsString(statistics.get("edit_comp")));
            historyRepository.save(history);
        } catch (Exception e) {
            e.printStackTrace();
        }
        trafficInterceptor.resetTrafficCnt();
    }

    private int getTrafficCnt(){
        return trafficInterceptor.getTrafficCnt();
    }

    private int resumeEditCntByStatus(){
        return adminResumeEditRepository.countRecords();
    }

    /* 유저별 각 비율 */
    private Map<String, Object> getUserMode() {
        return adminService.modeCnt();
    }

    private Map<String, Object> getUserStatus(){
        return adminService.statusCnt();
    }

    private Map<String, Object> getUserGender(){
        return adminService.genderCnt();
    }

    private Map<String, Object> getUserAge(){
        return adminService.ageCnt();
    }

    private Map<String, Map<String, Integer>> getUserOccu(){
        return adminService.rankOccup();
    }

    private Map<String, Map<String, Integer>> getUserComp(){
        return adminService.rankComp();
    }

    private Map<String, Map<String, Integer>> getUserWish(){
        return  adminService.rankWish();
    }

    /* OO별 첨삭 비율 */
    private Map<String, Object> getEditMode() {
        return adminService.resumeEditCntByMode();
    }

    private Map<String, Object> getEditStatus() {
        return adminService.resumeEditCntByStatus();
    }

    private Map<String, Object> getEditAge() {
        return adminService.resumeEditCntByAge();
    }

    private Map<String, Object> getEditDate() {
        Map<String, Object> result = new LinkedHashMap<>();

        // 월별 첨삭 비율 가져오기
        Map<String, Object> monthlyData = adminService.resumeCntByMonth();
        Map<String, Object> monthlyRatios = (Map<String, Object>) monthlyData.get("edit_ratio");

        // 주차별 첨삭 비율 가져오기
        Map<String, Object> weeklyData = adminService.resumeCntByWeekly();
        Map<String, Object> weeklyRatios = (Map<String, Object>) weeklyData.get("edit_ratio");

        // 일별 첨삭 비율 가져오기
        Map<String, Object> dailyData = adminService.resumeCntByDaily();
        Map<String, Object> dailyRatios = (Map<String, Object>) dailyData.get("edit_ratio");

        // 통합 결과 생성
        result.put("monthly_ratio", monthlyRatios);
        result.put("weekly_ratio", weeklyRatios);
        result.put("daily_ratio", dailyRatios);

        return result;
    }

    private Map<String, Map<String, Integer>> getEditOccu() {
        return adminService.rankOccup();
    }

    private Map<String, Map<String, Integer>> getEditComp() {
        return adminService.rankComp();
    }

    // ---------------------------------------------
    // 통계 데이터 출력

    /* 총 방문자 수 */
    @Override
    public Map<String, Object> getTotalTraffic() {
        Map<String, Object> result = new HashMap<>();

        long totalTraffic = historyRepository.findTotalTraffic();
        result.put("total_visit", totalTraffic);

        return result;
    }

    /* 오늘 방문자 수 */
    @Override
    public Map<String, Object> getTrafficForCurrentDate() {
        Map<String, Object> result = new HashMap<>();

        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Long todayTraffic = historyRepository.findTrafficByCurrentDate(currentDate);
        result.put("today_visit", todayTraffic);

        return result;
    }

    /* 일별 접속자 집계 */
    @Override
    public Map<LocalDate, Integer> getTrafficData(LocalDate startDate, LocalDate endDate) {
        // LocalDate를 Date로 변환하여 하루의 시작과 끝으로 설정
        ZoneId zoneId = ZoneId.systemDefault(); // 시스템 기본 시간대를 사용
        Date startDateTime = Date.from(startDate.atStartOfDay(zoneId).toInstant());
        Date endDateTime = Date.from(endDate.plusDays(1).atStartOfDay(zoneId).minusSeconds(1).toInstant());

        List<History> trafficData = historyRepository.findTrafficDataBetweenDates(startDateTime, endDateTime);
        return trafficData.stream()
                .collect(Collectors.toMap(
                        history -> history.getW_date().toInstant().atZone(zoneId).toLocalDate(),
                        History::getTraffic,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    /* 일별 회원가입 집계 */
    @Override
    public Map<LocalDate, Integer> getDailyUserRegistrations(LocalDate startDate, LocalDate endDate) {
        // startDate를 LocalDateTime으로 변환
        LocalDateTime startDateTime = startDate.atStartOfDay();

        // endDate를 다음 날의 시작으로 LocalDateTime으로 변환
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();

        // userRepository.findByInDateBetween() 메서드에 LocalDateTime 인스턴스를 전달
        List<User> users = userRepository.findByInDateBetween(startDateTime, endDateTime);

        Map<LocalDate, Integer> dailyRegistrations = new HashMap<>();
        for (User user : users) {
            LocalDate registrationDate = user.getInDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            dailyRegistrations.merge(registrationDate, 1, Integer::sum);
        }

        // 날짜별로 정렬된 맵 반환
        return new TreeMap<>(dailyRegistrations);
    }
}
