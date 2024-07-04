package com.team2.resumeeditorproject.admin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.resumeeditorproject.admin.domain.History;
import com.team2.resumeeditorproject.admin.domain.Traffic;
import com.team2.resumeeditorproject.admin.dto.HistoryDTO;
import com.team2.resumeeditorproject.admin.repository.AdminResumeBoardRepository;
import com.team2.resumeeditorproject.admin.repository.AdminResumeRepository;
import com.team2.resumeeditorproject.admin.repository.AdminUserRepository;
import com.team2.resumeeditorproject.admin.repository.HistoryRepository;
import com.team2.resumeeditorproject.admin.repository.TrafficRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistoryServiceImpl implements HistoryService{

    private final HistoryRepository historyRepository;
    private final AdminUserRepository userRepository;
    private final AdminResumeRepository resumeRepository;
    private final AdminResumeBoardRepository resumeBoardRepository;
    private final TrafficRepository trafficRepository;
    private final TrafficService trafficService;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final AdminService adminService;

    /* 통계 수집 */
    @Override
    @Transactional(readOnly = true)
    public void collectStatistics() {
        HistoryDTO historyDTO = new HistoryDTO();
        log.info("Starting collectStatistics");
        try {
            // 어제 날짜 가져오기
            LocalDate yesterday = LocalDate.now().minusDays(1);
            Traffic yesterdayTraffic = trafficRepository.findByInDate(yesterday);

            if (yesterdayTraffic != null) {
                historyDTO.setTraffic(yesterdayTraffic.getVisitCount());
                historyDTO.setEdit_count(yesterdayTraffic.getEditCount());
                historyDTO.setTraffic_date(yesterdayTraffic.getInDate());
            }

            // 통계 데이터를 Map<String, Object> 형태로 수집
            historyDTO.setUser_mode(objectMapper.writeValueAsString(getUserMode()));
            historyDTO.setUser_status(objectMapper.writeValueAsString(getUserStatus()));
            historyDTO.setUser_gender(objectMapper.writeValueAsString(getUserGender()));
            historyDTO.setUser_age(objectMapper.writeValueAsString(getUserAge()));
            historyDTO.setUser_occu(objectMapper.writeValueAsString(getUserOccu().get("ranking_user")));
            historyDTO.setUser_comp(objectMapper.writeValueAsString(getUserComp().get("ranking_user")));
            historyDTO.setUser_wish(objectMapper.writeValueAsString(getUserWish().get("ranking_user")));
            historyDTO.setEdit_mode(objectMapper.writeValueAsString(getEditMode().get("edit_ratio")));
            historyDTO.setEdit_status(objectMapper.writeValueAsString(getEditStatus().get("edit_ratio")));
            historyDTO.setEdit_age(objectMapper.writeValueAsString(getEditAge().get("age_edit_ratio")));
            historyDTO.setEdit_date(objectMapper.writeValueAsString(getEditDate()));
            historyDTO.setEdit_occu(objectMapper.writeValueAsString(getEditOccupation().get("ranking_resumeEdit")));
            historyDTO.setEdit_comp(objectMapper.writeValueAsString(getEditCompany().get("ranking_resumeEdit")));
            historyDTO.setW_date(new java.util.Date());

            // HistoryDTO를 History로 변환
            History history = modelMapper.map(historyDTO, History.class);

            // 데이터 저장
            historyRepository.save(history);

            log.info("Completed collectAndSaveStatistics successfully for date: {}", historyDTO.getTraffic_date());

        } catch (Exception e) {
            log.error("Error occurred while collecting statistics", e);
        }
    }

    /* 유저별 각 비율 */
    private Map<String, Object> getUserMode() {
        return adminService.getModeCount();
    }

    private Map<String, Object> getUserStatus(){
        return adminService.getStatusCount();
    }

    private Map<String, Object> getUserGender(){
        return adminService.getGenderCount();
    }

    private Map<String, Object> getUserAge(){
        return adminService.getAgeCount();
    }

    private Map<String, Map<String, Integer>> getUserOccu(){
        return adminService.getOccupationRank();
    }

    private Map<String, Map<String, Integer>> getUserComp(){
        return adminService.getCompanyRank();
    }

    private Map<String, Map<String, Integer>> getUserWish(){
        return  adminService.getWishRank();
    }

    /* OO별 첨삭 비율 */
    private Map<String, Object> getEditMode() {
        return adminService.getResumeEditCountByMode();
    }

    private Map<String, Object> getEditStatus() {
        return adminService.getResumeEditCountByStatus();
    }

    private Map<String, Object> getEditAge() {
        return adminService.getResumeEditCountByAge();
    }

    private Map<String, Object> getEditDate() {
        Map<String, Object> result = new LinkedHashMap<>();

        // 월별 첨삭 비율 가져오기
        Map<String, Object> monthlyData = adminService.getMonthlyResumeEditCount();
        Map<String, Object> monthlyRatios = (Map<String, Object>) monthlyData.get("edit_ratio");

        // 주차별 첨삭 비율 가져오기
        Map<String, Object> weeklyData = adminService.getWeeklyResumeEditCount();
        Map<String, Object> weeklyRatios = (Map<String, Object>) weeklyData.get("edit_ratio");

        // 일별 첨삭 비율 가져오기
        Map<String, Object> dailyData = adminService.getDailyResumeEditCount();
        Map<String, Object> dailyRatios = (Map<String, Object>) dailyData.get("edit_ratio");

        // 통합 결과 생성
        result.put("monthly_ratio", monthlyRatios);
        result.put("weekly_ratio", weeklyRatios);
        result.put("daily_ratio", dailyRatios);

        return result;
    }

    private Map<String, Map<String, Integer>> getEditOccupation() {
        return adminService.getOccupationRank();
    }

    private Map<String, Map<String, Integer>> getEditCompany() {
        return adminService.getCompanyRank();
    }

    // ---------------------------------------------
    // 통계 데이터 출력
    /* 프로 유저 수 */
    @Override
    public Map<String, Object> getProUserCount() {
        Map<String, Object> result = new HashMap<>();

        int proUser = userRepository.findByMode(2).size();

        result.put("pro", proUser);

        return result;
    }

    /* 총 방문자 수 */
    @Override
    public Map<String, Object> getTotalVisitCount() {
        Map<String, Object> result = new HashMap<>();
        long totalTraffic = trafficService.getTotalVisitCount();
        result.put("total_visit", totalTraffic);
        return result;
    }

    /* 총 첨삭 수 */
    @Override
    public Map<String, Object> getTotalEditCount() {
        Map<String, Object> result = new LinkedHashMap<>();

        long editCount = resumeRepository.count();

        result.put("edit_count", editCount);

        return result;
    }

    /* 오늘 첨삭 수 */
    @Override
    public Map<String, Object> getEditCountForCurrentDate() {
        Map<String, Object> result = new LinkedHashMap<>();

        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        long editCount = resumeRepository.findRNumByCurrentDate(currentDate);

        result.put("edit_count", editCount);

        return result;
    }

    /* 총 게시글 수 */
    @Override
    public Map<String, Object> getTotalBoardCount() {
        Map<String, Object> result = new HashMap<>();

        long totalBoardCnt = resumeBoardRepository.count();
        result.put("total_board", totalBoardCnt);

        return result;
    }

    /* 월별 첨삭 집계 */
    @Override
    public Map<String, Object> getMonthlyEditStatistics() {
        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, Integer> editCounts = new LinkedHashMap<>();
        Map<String, Double> editRatios = new LinkedHashMap<>();


        List<Object[]> monthList = resumeRepository.findMonthlyCorrectionCounts();

        // 전체 첨삭 횟수 계산
        int totalCorrections = monthList.stream().mapToInt(row -> ((Number) row[1]).intValue()).sum();

        // 월별 첨삭 횟수 및 비율 계산
        for (Object[] row : monthList) {
            String month = (String) row[0];
            int monthCnt = ((Number) row[1]).intValue();
            double monthRatio = ((double) monthCnt / totalCorrections) * 100;

            editCounts.put(month, monthCnt);
            editRatios.put(month, Math.round(monthRatio * 100) / 100.0);
        }

        Map<String, Object> combinedCounts = new LinkedHashMap<>();
        Map<String, Object> combinedRatios = new LinkedHashMap<>();

        result.put("edit_cnt", editCounts);
        result.put("edit_ratio", editRatios);

        return result;
    }

    /* 주별 첨삭 집계 */
    @Override
    public Map<String, Object> getWeeklyEditCount(String month) {
        Map<String, Object> result = new LinkedHashMap<>();

        // 월별 첨삭 데이터 가져오기
        Map<String, Object> editMonthly = adminService.getMonthlyResumeEditCount();

        // month가 null 또는 빈 값일 경우 현재 달로 설정
        if (month == null || month.isEmpty()) {
            LocalDate currentDate = LocalDate.now();
            month = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        }

        boolean dataExists = false;

        // edit_cnt에서 주어진 month의 weekly 데이터 추출
        if (editMonthly != null) {
            Map<String, Object> editCnt = (Map<String, Object>) editMonthly.get("edit_cnt");
            if (editCnt != null && editCnt.containsKey("weekly")) {
                Map<String, Object> editCntWeekly = (Map<String, Object>) editCnt.get("weekly");
                if (editCntWeekly != null && editCntWeekly.containsKey(month)) {
                    Map<String, Object> editCntResult = new LinkedHashMap<>();
                    editCntResult.put(month, editCntWeekly.get(month));
                    result.put("edit_cnt", editCntResult);
                    dataExists = true;
                }
            }

            // edit_ratio에서 주어진 month의 weekly 데이터 추출
            Map<String, Object> editRatio = (Map<String, Object>) editMonthly.get("edit_ratio");
            if (editRatio != null && editRatio.containsKey("weekly")) {
                Map<String, Object> editRatioWeekly = (Map<String, Object>) editRatio.get("weekly");
                if (editRatioWeekly != null && editRatioWeekly.containsKey(month)) {
                    Map<String, Object> editRatioResult = new LinkedHashMap<>();
                    editRatioResult.put(month, editRatioWeekly.get(month));
                    result.put("edit_ratio", editRatioResult);
                    dataExists = true;
                }
            }
        }

        if (!dataExists) {
            throw new IllegalArgumentException("해당 월의 데이터가 없습니다.");
        }

        return result;
    }

    /* 일별 첨삭 집계 */
    @Override
    public Map<String, Object> getDailyEditCount(String startDate, String endDate) {
        Map<String, Object> editDaily = adminService.getDailyResumeEditCount();
        Map<String, Object> result = new LinkedHashMap<>();

        // startDate와 endDate가 주어지지 않은 경우 현재 날짜를 기준으로 -6(총 7일)의 데이터를 설정
        LocalDate start = (startDate != null && !startDate.isEmpty()) ? LocalDate.parse(startDate) : LocalDate.now().minusDays(6);
        LocalDate end = (endDate != null && !endDate.isEmpty()) ? LocalDate.parse(endDate) : LocalDate.now();

        // "edit_cnt" 데이터 처리
        Map<String, Integer> editCntByDate = (Map<String, Integer>) editDaily.get("edit_cnt");
        Map<String, Integer> filteredEditCntData = new LinkedHashMap<>();

        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            String dateString = date.toString();
            filteredEditCntData.put(dateString, editCntByDate.getOrDefault(dateString, 0));
        }

        // "edit_ratio" 데이터 처리
        Map<String, Double> editRatioByDate = (Map<String, Double>) editDaily.get("edit_ratio");
        Map<String, Double> filteredEditRatioData = new LinkedHashMap<>();

        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            String dateString = date.toString();
            filteredEditRatioData.put(dateString, editRatioByDate.getOrDefault(dateString, 0.0));
        }

        // 결과에 데이터 추가
        result.put("edit_cnt", filteredEditCntData);
        result.put("edit_ratio", filteredEditRatioData);

        return result;
    }
}
