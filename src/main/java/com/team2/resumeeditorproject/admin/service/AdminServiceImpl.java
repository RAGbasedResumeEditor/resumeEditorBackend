package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminResumeBoardRepository;
import com.team2.resumeeditorproject.admin.repository.AdminResumeEditRepository;
import com.team2.resumeeditorproject.admin.repository.AdminResumeRepository;
import com.team2.resumeeditorproject.admin.repository.AdminUserRepository;
import com.team2.resumeeditorproject.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
// 통계에 맞는 클래스명으로 변경 예정
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService { //관리자 페이지 통계 데이터 처리해주는 클래스

    private final AdminUserRepository adminUserRepository;
    private final AdminResumeEditRepository adminResumeEditRepository;
    private final AdminResumeBoardRepository adminResumeBoardRepository;
    private final AdminResumeRepository adminResumeRepository;

    static int totalUserCount(AdminUserRepository adminUserRepository) {
        return adminUserRepository.countUsers(); // count~ 쿼리로 변경
    }

    /* 총 회원수 */
    @Override
    public Map<String, Object> getUserCount() {
        Map<String, Object> result = new HashMap<>();
        result.put("total_user", totalUserCount(adminUserRepository));
        return result;
    }

    /* 성비 */
    @Override
    public Map<String, Object> getGenderCount() {
        int userCnt = totalUserCount(adminUserRepository);
        double female = ((double) adminUserRepository.findByGender('F').size() / userCnt) * 100;
        double male = ((double) adminUserRepository.findByGender('M').size() / userCnt) * 100;

        Map<String, Object> result = new HashMap<>();
        result.put("female", Math.round(female * 100.0) / 100.0);
        result.put("male", Math.round(male * 100.0) / 100.0);
        return result;
    }

    /* 직업별 유저 수, 첨삭 수 랭킹 5 */
    @Override
    public Map<String, Map<String, Integer>> getOccupationRank(){
        List<String> userOccupation = adminUserRepository.findOccupations(); // User 테이블에서
        List<String> resumeEditOccupation = adminResumeEditRepository.findOccupations(); // ResumeEdit 테이블에서

        // 직업별 유저 수, 직업별 첨삭 수
        Map<String, Integer> userCnt = new HashMap<>();
        Map<String, Integer> editCnt = new HashMap<>();
        for (String occupation : userOccupation) {
            userCnt.put((occupation.isEmpty()) ? "무직" : occupation, adminUserRepository.findByOccupation(occupation).size());
        }
        for (String occupation:resumeEditOccupation) {
            editCnt.put(occupation,adminResumeEditRepository.findByOccupation(occupation).size());
        }
        // value 오름차순 정렬
        List<String> userKeys = new ArrayList<>(userCnt.keySet());
        List<String> editKeys = new ArrayList<>(editCnt.keySet());
        Collections.sort(userKeys, (v1, v2) -> (userCnt.get(v2).compareTo(userCnt.get(v1))));
        Collections.sort(editKeys, (v1, v2) -> (editCnt.get(v2).compareTo(editCnt.get(v1))));

        Map<String, Integer> RankingUser = new HashMap<>(); // user 수 rank 담을 List
        Map<String, Integer> RankingResumeEdit = new HashMap<>(); // resumeEdit 수 rank 담을 List
        for (int i = 1; i <= 5; i++) { // 1 ~ 5 순위까지 각 List에 더한다.
            RankingUser.put(userKeys.get(i - 1), userCnt.get(userKeys.get(i - 1)));
            RankingResumeEdit.put(editKeys.get(i - 1), editCnt.get(editKeys.get(i - 1)));
        }

        Map<String,Map<String, Integer>> result = new HashMap<>();
        result.put("ranking_user", RankingUser);
        result.put("ranking_resumeEdit", RankingResumeEdit);

        return result;
    }


    /* 회사별 유저 수, 첨삭 수 랭킹 5 */
    @Override
    public Map<String, Map<String, Integer>> getCompanyRank() {
        List<String> userCompany = adminUserRepository.findCompanies(); // User 테이블에서
        List<String> resumeEditCompany = adminResumeEditRepository.findCompanies(); // ResumeEdit 테이블에서

        // 회사별 유저 수, 회사별 첨삭 수
        Map<String, Integer> userCnt = new HashMap<>();
        Map<String, Integer> editCnt=new HashMap<>();
        for (String company : userCompany) {
            userCnt.put((company.isEmpty()) ? "없음" : company, adminUserRepository.findByCompany(company).size());
        }
        for (String company : resumeEditCompany) {
            editCnt.put(company, adminResumeEditRepository.findByCompany(company).size());
        }

        // value 오름차순 정렬
        List<String> userKeys = new ArrayList<>(userCnt.keySet());
        List<String> editKeys = new ArrayList<>(editCnt.keySet());
        Collections.sort(userKeys, (v1, v2) -> (userCnt.get(v2).compareTo(userCnt.get(v1))));
        Collections.sort(editKeys, (v1, v2) -> (editCnt.get(v2).compareTo(editCnt.get(v1))));

        Map<String, Integer> userRanking = new HashMap<>(); // user 수 rank 담을 List
        Map<String, Integer> resumeEditRanking = new HashMap<>(); // resumeEdit 수 rank 담을 List
        for (int i = 1; i <= 5; i++) { // 1 ~ 5 순위까지 각 List에 더한다.
            userRanking.put(userKeys.get(i - 1),userCnt.get(userKeys.get(i - 1)));
            resumeEditRanking.put(editKeys.get(i - 1),editCnt.get(editKeys.get(i - 1)));
        }

        Map<String, Map<String, Integer>> result = new HashMap<>();
        result.put("ranking_user", userRanking);
        result.put("ranking_resumeEdit", resumeEditRanking);

        return result;
    }

    /* 희망 회사별 유저 수 랭킹 5 */
    @Override
    public Map<String, Map<String, Integer>> getWishRank() {
        List<String> wishes = adminUserRepository.findWishes();
        Map<String, Integer> userCnt = new HashMap<>();

        for (String wish : wishes) {
            userCnt.put((wish.isEmpty()) ? "없음" : wish, adminUserRepository.findByWish(wish).size());
        }

        // value 기준 오름차순 정렬
        List<String> wKeys = new ArrayList<>(userCnt.keySet());
        Collections.sort(wKeys, (v1, v2) -> (userCnt.get(v2).compareTo(userCnt.get(v1))));

        Map<String, Integer> wishCnt = new HashMap<>();

        for (int i = 1; i <= 5; i++) { // 유저 수 top 5
            wishCnt.put(wKeys.get(i - 1), userCnt.get(wKeys.get(i - 1)));
        }

        Map<String,Map<String, Integer>> result = new HashMap<>();
        result.put("ranking_user",wishCnt);

        return result;
    }

    /* 연령대 */
    @Override
    public Map<String, Object> getAgeCount() {
        int userCnt = totalUserCount(adminUserRepository);
        Map<String, Object> result = new HashMap<>();
        double ageRatio = 0;
        for (int age = 20; age <= 50; age += 10) {
            ageRatio = ((double)adminUserRepository.findByAgeBetween(age, age + 9).size() / (double) userCnt) * 100;
            result.put(age + "", Math.round(ageRatio * 100.0) / 100.0);
        }
        ageRatio = ((double)adminUserRepository.findByAgeBetween(60, 99).size() / (double) userCnt) * 100;
        result.put("over_sixty", Math.round(ageRatio * 100.0) / 100.0);
        return result;
    }

    /* 신입 경력 비율 */
    @Override
    public Map<String, Object> getStatusCount() {
        int userCnt = totalUserCount(adminUserRepository);
        double unemplyed = ((double)adminUserRepository.findByStatus(1).size() / (double) userCnt) * 100; // 구직자 비율
        double employed = ((double)adminUserRepository.findByStatus(2).size() / (double) userCnt) * 100; // 이직자 비율
        Map<String, Object> result = new HashMap<>();
        result.put("unemplyed", Math.round(unemplyed * 100.0) / 100.0);
        result.put("employed", Math.round(employed * 100.0) / 100.0);
        return result;
    }

    /* 프로 라이트 모드 비율 */
    @Override
    public Map<String, Object> getModeCount() {
        int userCnt = totalUserCount(adminUserRepository);
        double light = ((double)adminUserRepository.findByMode(1).size() / (double) userCnt) * 100;
        double pro = ((double)adminUserRepository.findByMode(2).size() / (double) userCnt) * 100;
        Map<String, Object> result = new HashMap<>();
        result.put("pro", Math.round(pro * 100.0) / 100.0);
        result.put("light", Math.round(light * 100.0) / 100.0);
        return result;
    }

    /* 3) 자소서 첨삭 이용 통계 */
    private long countResumeEdits(List<User> userList) {
        long totalResumeEdits = 0;
        for (User user : userList) {
            totalResumeEdits += adminResumeEditRepository.countByUNum(user.getUNum());
        }
        return totalResumeEdits;
    }

    /* 신입/경력 별 첨삭 횟수,비율 */
    @Override
    public Map<String, Object> getResumeEditCountByStatus() {
        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, Object> editCounts = new LinkedHashMap<>();
        Map<String, Object> editRatios = new LinkedHashMap<>();

        // 신입(구직) 사용자 조회
        List<User> newUserList = adminUserRepository.findByStatus(1);
        long newWorkerCnt = countResumeEdits(newUserList);

        // 경력(이직) 사용자 조회
        List<User> experiencedUserList = adminUserRepository.findByStatus(2);
        long experiencedWorkerCnt = countResumeEdits(experiencedUserList);

        long totalEditCnt = newWorkerCnt + experiencedWorkerCnt;
        double newWorkerRatio = ((double) newWorkerCnt / totalEditCnt) * 100;
        double experiencedWorkerRatio = ((double) experiencedWorkerCnt / totalEditCnt) * 100;

        editCounts.put("status_1", newWorkerCnt);
        editCounts.put("status_2", experiencedWorkerCnt);
        editRatios.put("status_1", Math.round(newWorkerRatio * 100.0) / 100.0);
        editRatios.put("status_2", Math.round(experiencedWorkerRatio * 100.0) / 100.0);

        result.put("edit_cnt", editCounts);
        result.put("edit_ratio", editRatios);

        return result;
    }

    /* 연령 별 첨삭 횟수,비율 */
    @Override
    public Map<String, Object> getResumeEditCountByAge() {
        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, Long> ageGroupCounts = new LinkedHashMap<>();

        ageGroupCounts.put("20", 0L);
        ageGroupCounts.put("30", 0L);
        ageGroupCounts.put("40", 0L);
        ageGroupCounts.put("50", 0L);
        ageGroupCounts.put("over_sixty", 0L);

        List<User> users = adminUserRepository.findAll();

        for (User user : users) {
            int age = user.getAge();
            String ageGroup = switch (age / 10) {
                case 2 -> "20";
                case 3 -> "30";
                case 4 -> "40";
                case 5 -> "50";
                default -> age >= 60 ? "over_sixty" : null;
            };

            if (ageGroup != null) {
                ageGroupCounts.put(ageGroup, ageGroupCounts.get(ageGroup) + adminResumeEditRepository.countByUNum(user.getUNum()));
            }
        }

        long totalEdits = ageGroupCounts.values().stream().mapToLong(Long::longValue).sum();

        Map<String, Double> ageGroupRatios = new LinkedHashMap<>();
        for (String ageGroup : ageGroupCounts.keySet()) {
            long count = ageGroupCounts.get(ageGroup);
            ageGroupRatios.put(ageGroup, totalEdits == 0 ? 0.0 : (double) count / totalEdits * 100);
        }

        result.put("age_edit_cnt", ageGroupCounts);
        result.put("age_edit_ratio", ageGroupRatios);

        return result;
    }

    /* 모드 별 첨삭 횟수,비율*/
    @Override
    public Map<String, Object> getResumeEditCountByMode() {
        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, Object> editCounts = new LinkedHashMap<>();
        Map<String, Object> editRatios = new LinkedHashMap<>();

        // light모드 사용자 조회
        List<User> lightList = adminUserRepository.findByMode(1);
        long lightCnt = countResumeEdits(lightList);

        // pro모드 사용자 조회
        List<User> proList = adminUserRepository.findByMode(2);
        long proCnt = countResumeEdits(proList);

        long totalEditCnt = lightCnt + proCnt;
        double lightRatio = ((double) lightCnt / totalEditCnt) * 100;
        double proRatio = ((double) proCnt / totalEditCnt) * 100;

        editCounts.put("mode_light", lightCnt);
        editCounts.put("mode_pro", proCnt);

        editRatios.put("mode_light", Math.round(lightRatio * 100.0) / 100.0);
        editRatios.put("mode_pro", Math.round(proRatio * 100.0) / 100.0);

        result.put("edit_cnt", editCounts);
        result.put("edit_ratio", editRatios);

        return result;
    }

    // 해당 월의 주차를 계산하는 메서드
    private int getWeekOfMonth(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return localDate.get(WeekFields.of(Locale.getDefault()).weekOfMonth());
    }

    /* 채용시즌(월별, 주차별, 일별) 첨삭 횟수,비율 */
    @Override
    public Map<String, Object> getMonthlyResumeEditCount() {
        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, Integer> editCounts = new LinkedHashMap<>();
        Map<String, Double> editRatios = new LinkedHashMap<>();

        // 월별 첨삭 횟수 조회
        List<Object[]> monthList = adminResumeRepository.findMonthlyCorrectionCounts();
        // 주차별 첨삭 횟수 조회
        List<Object[]> weeklyList = adminResumeRepository.findWeeklyCorrectionCounts();
        // 일별 첨삭 횟수 조회
        List<Object[]> dailyList = adminResumeRepository.findDailyCorrectionCounts();

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

        // 주차별 첨삭 횟수 및 비율 계산
        Map<String, Map<String, Integer>> weeklyEditCounts = new LinkedHashMap<>();
        Map<String, Map<String, Double>> weeklyEditRatios = new LinkedHashMap<>();

        for (Object[] row : weeklyList) {
            String month = (String) row[0];
            String week = "w0" + ((Number) row[1]).intValue();
            int weeklyCnt = ((Number) row[2]).intValue();

            // 월별 맵에 주차별 첨삭 횟수 저장
            weeklyEditCounts.computeIfAbsent(month, k -> new TreeMap<>());
            weeklyEditRatios.computeIfAbsent(month, k -> new TreeMap<>());

            Map<String, Integer> weekDataCounts = weeklyEditCounts.get(month);
            weekDataCounts.put(week, weeklyCnt);

            // 주차별 첨삭 비율 계산
            int monthTotalCnt = editCounts.getOrDefault(month, 0);
            double weeklyRatio = monthTotalCnt == 0 ? 0 : ((double) weeklyCnt / monthTotalCnt) * 100;
            Map<String, Double> weekDataRatios = weeklyEditRatios.get(month);
            weekDataRatios.put(week, Math.round(weeklyRatio * 100) / 100.0);
        }

        // 일별 첨삭 횟수 및 비율 계산
        Map<String, Map<String, Integer>> dailyEditCounts = new LinkedHashMap<>();
        Map<String, Map<String, Double>> dailyEditRatios = new LinkedHashMap<>();

        for (Object[] row : dailyList) {
            String date = (String) row[0];
            String month = date.substring(0, 7);
            int week = getWeekOfMonth(date);
            int dailyCnt = ((Number) row[1]).intValue();

            String weekKey = "w0" + week;
            String dayKey = date;

            // 월별 맵에 일별 첨삭 횟수 저장
            dailyEditCounts.computeIfAbsent(month, k -> new LinkedHashMap<>());
            dailyEditRatios.computeIfAbsent(month, k -> new LinkedHashMap<>());

            Map<String, Integer> dayDataCounts = dailyEditCounts.get(month);
            dayDataCounts.put(dayKey, dailyCnt);

            // 일별 첨삭 비율 계산
            int monthTotalCnt = editCounts.getOrDefault(month, 0);
            double dailyRatio = monthTotalCnt == 0 ? 0 : ((double) dailyCnt / monthTotalCnt) * 100;
            Map<String, Double> dayDataRatios = dailyEditRatios.get(month);
            dayDataRatios.put(dayKey, Math.round(dailyRatio * 100) / 100.0);
        }

        // 데이터가 없는 경우 0으로 초기화
        for (String month : editCounts.keySet()) {
            weeklyEditCounts.putIfAbsent(month, new LinkedHashMap<>());
            weeklyEditRatios.putIfAbsent(month, new LinkedHashMap<>());
            dailyEditCounts.putIfAbsent(month, new LinkedHashMap<String, Integer>());
            dailyEditRatios.putIfAbsent(month, new LinkedHashMap<String, Double>());

            // 주차별 데이터가 없는 경우 0으로 초기화
            for (int i = 1; i <= 5; i++) { // 최대 5주차까지 고려
                String weekKey = "w0" + i;
                weeklyEditCounts.get(month).putIfAbsent(weekKey, 0);
                weeklyEditRatios.get(month).putIfAbsent(weekKey, 0.0);
            }

            // 일별 데이터가 없는 경우 0으로 초기화
            int daysInMonth = YearMonth.parse(month).lengthOfMonth();
            for (int day = 1; day <= daysInMonth; day++) {
                String dayKey = month + "-" + (day < 10 ? "0" + day : day);
                dailyEditCounts.get(month).putIfAbsent(dayKey, 0);
                dailyEditRatios.get(month).putIfAbsent(dayKey, 0.0);
            }
        }

        Map<String, Object> combinedCounts = new LinkedHashMap<>();
        Map<String, Object> combinedRatios = new LinkedHashMap<>();

        combinedCounts.put("monthly", editCounts);
        combinedCounts.put("weekly", weeklyEditCounts);
        combinedCounts.put("daily", dailyEditCounts);

        combinedRatios.put("monthly", editRatios);
        combinedRatios.put("weekly", weeklyEditRatios);
        combinedRatios.put("daily", dailyEditRatios);

        result.put("edit_cnt", combinedCounts);
        result.put("edit_ratio", combinedRatios);

        return result;
    }

    /* 채용시즌(주차별) 첨삭 횟수 */
    @Override
    public Map<String, Object> getWeeklyResumeEditCount() {
        Map<String, Object> result = new LinkedHashMap<>();

        // 일별 첨삭 횟수 조회
        List<Object[]> dailyList = adminResumeRepository.findDailyCorrectionCounts();

        // 주차별 일자 데이터 초기화
        for (Object[] row : dailyList) {
            String date = (String) row[0]; // 일자
            int count = ((Number) row[1]).intValue(); // 해당 일자의 첨삭 횟수
            String month = date.substring(0, 7); // 연도와 월만 가져옴
            String week = "w0" + getWeekOfMonth(date); // 주차 계산

            // 결과 맵에 데이터 추가
            result.putIfAbsent(month, new LinkedHashMap<>());
            Map<String, Map<String, Object>> monthData = (Map<String, Map<String, Object>>) result.get(month);
            monthData.putIfAbsent(week, new TreeMap<>()); // 날짜순으로 정렬
            Map<String, Object> weekData = monthData.get(week);
            weekData.put(date, count); // 해당 주차의 날짜에 첨삭 횟수 저장
        }

        // 데이터가 없는 경우 0으로 초기화
        for (String month : result.keySet()) {
            Map<String, Map<String, Object>> monthData = (Map<String, Map<String, Object>>) result.get(month);
            int daysInMonth = LocalDate.parse(month + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")).lengthOfMonth();
            for (int day = 1; day <= daysInMonth; day++) {
                String date = month + "-" + (day < 10 ? "0" + day : day);
                String week = "w0" + getWeekOfMonth(date);
                monthData.putIfAbsent(week, new TreeMap<>());
                Map<String, Object> weekData = monthData.get(week);
                if (!weekData.containsKey(date) && isValidDate(date)) {
                    weekData.put(date, 0);
                }
            }
            monthData = new TreeMap<>(monthData);
            result.put(month, monthData);
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("edit_cnt", result);

        // edit_ratio 데이터 계산 및 추가
        Map<String, Object> editRatio = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : result.entrySet()) {
            Map<String, Map<String, Object>> monthData = (Map<String, Map<String, Object>>) entry.getValue();
            Map<String, Map<String, Double>> monthRatioData = new LinkedHashMap<>();
            for (Map.Entry<String, Map<String, Object>> weekEntry : monthData.entrySet()) {
                Map<String, Object> weekData = weekEntry.getValue();
                Map<String, Double> weekRatioData = new LinkedHashMap<>();
                int totalCorrections = weekData.values().stream().mapToInt(value -> (int) value).sum();
                for (Map.Entry<String, Object> dayEntry : weekData.entrySet()) {
                    double ratio = totalCorrections == 0 ? 0 : ((int) dayEntry.getValue() / (double) totalCorrections) * 100;
                    weekRatioData.put(dayEntry.getKey(), Math.round(ratio * 100.0) / 100.0);
                }
                monthRatioData.put(weekEntry.getKey(), new TreeMap<>(weekRatioData)); // 날짜순으로 정렬
            }
            editRatio.put(entry.getKey(), monthRatioData); // 월별 edit_ratio 데이터 저장
        }
        response.put("edit_ratio", editRatio);

        return response;
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /* 채용시즌(일별) 첨삭 횟수 */
    @Override
    public Map<String, Object> getDailyResumeEditCount() {
        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, Integer> editCounts = new LinkedHashMap<>();
        Map<String, Object> editRatios = new LinkedHashMap<>();

        List<Object[]> dailyList = adminResumeRepository.findDailyCorrectionCounts();

        int totalCorrections = dailyList.stream().mapToInt(row -> ((Number) row[1]).intValue()).sum();

        for (Object[] row : dailyList) {
            String day = (String) row[0];
            int dayCnt = ((Number) row[1]).intValue();
            double dayRatio = ((double) dayCnt / totalCorrections) * 100;

            editCounts.put(day, dayCnt);
            editRatios.put(day, Math.round(dayRatio * 100.0) / 100.0);
        }

        result.put("edit_cnt", editCounts);
        result.put("edit_ratio", editRatios);

        return result;
    }
}
