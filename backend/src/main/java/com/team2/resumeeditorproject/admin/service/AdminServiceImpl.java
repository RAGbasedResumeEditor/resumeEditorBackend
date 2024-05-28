package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.*;
import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import com.team2.resumeeditorproject.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{ //관리자 페이지 통계 데이터 처리해주는 클래스

    private final AdminUserRepository adminRepository;
    private final AdminResumeEditRepository adResEditRepository;
    private final AdminResumeBoardRepository adResBoardRepository;
    private final AdminResumeRepository adResRepository;

    public static int totalUserCnt(AdminUserRepository adminUserRepository){
        List<User> users = adminUserRepository.findAll();
        return users.size();
    }

    @Override
    public Map<String, Object> userCnt(){ // 총 회원수
        Map<String, Object> result=new HashMap<>();
        result.put("total_user", totalUserCnt(adminRepository));
        return result;
    }

    @Override
    public Map<String, Object> genderCnt() {  //성비
        int userCnt = totalUserCnt(adminRepository);
        double female = ((double) adminRepository.findByGender('F').size() / userCnt) * 100;
        double male = ((double) adminRepository.findByGender('M').size() / userCnt) * 100;

        Map<String, Object> result=new HashMap<>();
        result.put("female",Math.round(female*100.0)/100.0);
        result.put("male",Math.round(male*100.0)/100.0);
        return result;
    }

    @Override
    public Map<String,Object> occupCnt(String occupation) {  //occupation
        int userCnt = totalUserCnt(adminRepository);
        double occup=((double) adminRepository.findByOccupation(occupation).size() /userCnt) * 100;

        Map<String,Object> result=new HashMap<>();
        result.put(occupation,Math.round(occup*100.0)/100.0);

        return result;
    }

    @Override
    public Map<String, List<String>> rankOccup(){ //직업별 유저 수, 첨삭 수 랭킹 5을 보여주는 메서드
        //직업을 가져와 리스트에 담는다.
        List<String> uOccupation=adminRepository.findOccupations(); // User 테이블에서
        List<String> rOccupation=adResEditRepository.findOccupations(); // ResumeEdit 테이블에서
        //직업별 유저 수, 직업별 첨삭 수를 구한다.
        Map<String, Integer> userCnt=new HashMap<>();
        Map<String, Integer> editCnt=new HashMap<>();
        for(String occup:uOccupation){
            userCnt.put((occup.isEmpty())?"무직":occup,adminRepository.findByOccupation(occup).size());
        }
        for(String occup:rOccupation){
            editCnt.put(occup,adResEditRepository.findByOccupation(occup).size());
        }
        //value 오름차순 정렬한다.
        List<String> uKeys = new ArrayList<>(userCnt.keySet());
        List<String> rKeys = new ArrayList<>(editCnt.keySet());
        Collections.sort(uKeys, (v1, v2) -> (userCnt.get(v2).compareTo(userCnt.get(v1))));
        Collections.sort(rKeys, (v1, v2) -> (editCnt.get(v2).compareTo(editCnt.get(v1))));

        List<String> uRanking=new ArrayList<>(); // user 수 rank 담을 List
        List<String> rRanking=new ArrayList<>(); // resumeedit 수 rank 담을 List
        for(int i=1;i<=5;i++){ // 1 ~ 5 순위까지 각 List에 더한다.
            uRanking.add(uKeys.get(i-1));
            rRanking.add(rKeys.get(i-1));
        }

        Map<String,List<String>> result=new HashMap<>(); // 출력할 responseEntity
        result.put("ranking_user",uRanking);
        result.put("ranking_resumeEdit", rRanking);

        return result;
    }

    @Override
    public  Map<String, Object> wishCnt(String wish) {
        int userCnt = totalUserCnt(adminRepository);
        double wishes=((double) adminRepository.findByWish(wish).size() / (double) userCnt) * 100;

        Map<String, Object> result=new HashMap<>();
        result.put(wish,Math.round(wishes*100.0)/100.0);

        return result;
    }

    @Override
    public Map<String, List<String>> rankComp(){ //회사별 유저 수, 첨삭 수 랭킹 5을 보여주는 메서드
        //직업을 가져와 리스트에 담는다.
        List<String> uCompany=adminRepository.findCompanies(); // User 테이블에서
        List<String> rCompany=adResEditRepository.findCompanies(); // ResumeEdit 테이블에서

        //회사별 유저 수, 회사별 첨삭 수를 구한다.
        Map<String, Integer> userCnt=new HashMap<>();
        Map<String, Integer> editCnt=new HashMap<>();
        for(String company:uCompany){
            userCnt.put((company.isEmpty())?"없음":company,adminRepository.findByCompany(company).size());
        }
        for(String company:rCompany){
            editCnt.put(company,adResEditRepository.findByCompany(company).size());
        }

        //value 오름차순 정렬한다.
        List<String> uKeys = new ArrayList<>(userCnt.keySet());
        List<String> rKeys = new ArrayList<>(editCnt.keySet());
        Collections.sort(uKeys, (v1, v2) -> (userCnt.get(v2).compareTo(userCnt.get(v1))));
        Collections.sort(rKeys, (v1, v2) -> (editCnt.get(v2).compareTo(editCnt.get(v1))));

        List<String> uRanking=new ArrayList<>(); // user 수 rank 담을 List
        List<String> rRanking=new ArrayList<>(); // resumeedit 수 rank 담을 List
        for(int i=1;i<=5;i++){ // 1 ~ 5 순위까지 각 List에 더한다.
            uRanking.add(uKeys.get(i-1));
            rRanking.add(rKeys.get(i-1));
        }

        Map<String,List<String>> result=new HashMap<>();
        result.put("ranking_user",uRanking);
        result.put("ranking_resumeEdit", rRanking);

        return result;
    }

    @Override
    public Map<String, List<String>> rankWish() {
        //회사별 유저 수, 첨삭 수 랭킹 5을 보여주는 메서드 (변수 수정 예정)
        //희망직군을 가져와 리스트에 담는다.
        List<String> wishes=adminRepository.findWishes();
        //희망직군별 유저 수를 구한다.
        Map<String, Integer> userCnt=new HashMap<>();
        Map<String, Integer> editCnt=new HashMap<>();
        for(String wish:wishes){
            userCnt.put((wish.isEmpty())?"무직":wish,adminRepository.findByWish(wish).size());
        }
        //value 기준 오름차순 정렬한다.
        List<String> keys = new ArrayList<>(userCnt.keySet());
        Collections.sort(keys, (v1, v2) -> (userCnt.get(v2).compareTo(userCnt.get(v1))));
        Map<String,List<String>> result=new HashMap<>();
        //  for (String key : keys) {
        //     System.out.println(key + " : " + userCnt.get(key));
        //  }
        //  for (String key : keys2) {
        //      System.out.println(key + " : " + editCnt.get(key));
        //  }
        List<String> wishCnt=new ArrayList<>(); // 유저
        for(int i=1;i<=5;i++){
            wishCnt.add(keys.get(i-1));
        }
        result.put("ranking_user",wishCnt);

        return result;
    }
  
    @Override
    public Map<String, Object> ageCnt() {  //연령대
        int userCnt = totalUserCnt(adminRepository);
        Map<String, Object> result=new HashMap<>();
        double percentage=0;
        for(int age=20;age<=50;age+=10){
            percentage=((double)adminRepository.findByAgeBetween(age, age+9).size()/(double) userCnt)*100;
            result.put(age+"", Math.round(percentage*100.0)/100.0);
        }
        percentage=((double)adminRepository.findByAgeBetween(60, 99).size()/(double) userCnt)*100;
        result.put("over_sixty", Math.round(percentage*100.0)/100.0);
        return result;
    }

    @Override
    public Map<String, Object> statusCnt() {   //신입 경력 비율
        int userCnt = totalUserCnt(adminRepository);
        double uneployed = ((double)adminRepository.findByStatus(1).size()/(double)userCnt)*100; // 구직자 비율
        double employed = ((double)adminRepository.findByStatus(2).size()/(double)userCnt)*100; // 이직자 비율
        Map<String, Object> result=new HashMap<>();
        result.put("unemplyed",Math.round(uneployed*100.0)/100.0);
        result.put("employed",Math.round(employed*100.0)/100.0);
        return result;
    }

    @Override
    public Map<String, Object> modeCnt() {    //프로 라이트 모드 비율
        int userCnt = totalUserCnt(adminRepository);
        double light = ((double)adminRepository.findByMode(1).size()/(double)userCnt)*100;
        double pro = ((double)adminRepository.findByMode(2).size()/(double)userCnt)*100;
        Map<String, Object> result=new HashMap<>();
        result.put("pro",Math.round(pro*100.0)/100.0);
        result.put("light",Math.round(light*100.0)/100.0);
        return result;
    }


    @Override
    public Map<String, Object> CompResumeCnt(String company) { // 회사별 자소서 평점, 조회수

        if (company == null) {
            throw new IllegalArgumentException("Company parameter cannot be null");
        }

        List<ResumeEdit> resumeByCompany = adResEditRepository.findByCompany(company);
        List<Float> ratesByComp = new ArrayList<>();
        List<Integer> viewsByComp = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();

        resumeByCompany.forEach(resumeEdit -> {
            Long rn = resumeEdit.getR_num();
            adResBoardRepository.findById(rn).ifPresent(adResBoard -> {
                ratesByComp.add(adResBoard.getRating());
                viewsByComp.add(adResBoard.getRead_num());
            });
        });

        ratesByComp.sort(Comparator.reverseOrder());
        viewsByComp.sort(Comparator.reverseOrder());
        float sumRatesByCompany=0; int sumViewsByComp=0;

        for(Float rate:ratesByComp){
            sumRatesByCompany+=rate;
        }
        for(int view:viewsByComp){
            sumViewsByComp+=view;
        }

        int cnt= ratesByComp.size();
        float avgRate = cnt > 0 ? sumRatesByCompany / (float) cnt : 0;
        int avgViews = cnt > 0 ? sumViewsByComp / cnt : 0;

        result.put("resume_count",ratesByComp.size());
        result.put("resume_avgRate",String.format("%.2f", avgRate));
        result.put("resume_avgView",avgViews);
        result.put("highest_rate",ratesByComp.get(0));
        result.put("lowest_rate",ratesByComp.get(cnt-1));
        result.put("highest_view",viewsByComp.get(0));
        result.put("lowest_view",viewsByComp.get(cnt-1));

        return result;
    }

    @Override
    public Map<String, Object> OccupResumeCnt(String occupation) {  //직군별 자소서 평점, 조회수

        if (occupation == null) {
            throw new IllegalArgumentException("Company parameter cannot be null");
        }

        List<ResumeEdit> resumeByOccupation=adResEditRepository.findByOccupation(occupation);
        List<Float> ratesByOccup=new ArrayList<>();
        List<Integer> viewsByOccup=new ArrayList<>();
        Map<String, Object> result=new HashMap<>();

        resumeByOccupation.forEach(resumeEdit -> {
            Long rn = resumeEdit.getR_num();
            adResBoardRepository.findById(rn).ifPresent(adResBoard -> {
                ratesByOccup.add(adResBoard.getRating());
                viewsByOccup.add(adResBoard.getRead_num());
            });
        });

        ratesByOccup.sort(Comparator.reverseOrder());
        viewsByOccup.sort(Comparator.reverseOrder());

        float sumRatesByOccup=0; int sumViewsByOccup=0;

        for(Float rate:ratesByOccup){
            sumRatesByOccup+=rate;
        }
        for(int view:viewsByOccup){
            sumViewsByOccup+=view;
        }

        int cnt= ratesByOccup.size();
        float avgRate=cnt > 0 ? sumRatesByOccup / cnt : 0;
        int avgViews = cnt > 0 ? sumViewsByOccup / cnt : 0;

        result.put("resume_count",cnt);
        result.put("resume_avgRate",avgRate);
        result.put("resume_avgView",avgViews);
        result.put("highest_rate",ratesByOccup.get(0));
        result.put("lowest_rate",ratesByOccup.get(cnt-1));
        result.put("highest_view",viewsByOccup.get(0));
        result.put("lowest_view",viewsByOccup.get(cnt-1));
        return result;
    }

    /* 3) 자소서 첨삭 이용 통계 */
    private long countResumeEdits(List<User> userList) {
        long totalResumeEdits = 0;
        for (User user : userList) {
            totalResumeEdits += adResEditRepository.countByUNum(user.getUNum());
        }
        return totalResumeEdits;
    }

    /* 신입/경력 별 첨삭 횟수,비율 */
    @Override
    public Map<String, Object> resumeEditCntByStatus() {
        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, Object> editCounts = new LinkedHashMap<>();
        Map<String, Object> editRatios = new LinkedHashMap<>();

        // 신입(구직) 사용자 조회
        List<User> newUserList = adminRepository.findByStatus(1);
        long newWorkerCnt = countResumeEdits(newUserList);

        // 경력(이직) 사용자 조회
        List<User> experiencedUserList = adminRepository.findByStatus(2);
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

    /* 직군 별 첨삭 횟수,비율 */
    @Override
    public Map<String, Object> resumeEditCntByOccup(String occupation) {
        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, Object> editCounts = new LinkedHashMap<>();
        Map<String, Object> editRatios = new LinkedHashMap<>();

        int userCnt = totalUserCnt(adminRepository);
        List<User> occupList = adminRepository.findByOccupation(occupation);

        long occupCnt = countResumeEdits(occupList);
        double occupRatio = ((double) occupCnt / userCnt) * 100;

        editCounts.put(occupation, occupCnt);
        editRatios.put(occupation, Math.round(occupRatio * 100.0) / 100.0);

        result.put("occup_edit_cnt", editCounts);
        result.put("occup_edit_ratio", editRatios);

        return result;
    }

    /* 회사 별 첨삭 횟수,비율*/
    @Override
    public Map<String, Object> resumeEditCntByComp(String company) {
        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, Object> editCounts = new LinkedHashMap<>();
        Map<String, Object> editRatios = new LinkedHashMap<>();

        int userCnt = totalUserCnt(adminRepository);
        List<User> compList = adminRepository.findByCompany(company);

        long compCnt = countResumeEdits(compList);
        double compRatio = ((double) compCnt / userCnt) * 100;

        editCounts.put(company, compCnt);
        editRatios.put(company, Math.round(compRatio * 100.0) / 100.0);

        result.put("company_edit_cnt", editCounts);
        result.put("company_edit_ratio", editRatios);

        return result;
    }

    /* 연령 별 첨삭 횟수,비율 */
    @Override
    public Map<String, Object> resumeEditCntByAge() {
        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, Long> ageGroupCounts = new LinkedHashMap<>();

        ageGroupCounts.put("20", 0L);
        ageGroupCounts.put("30", 0L);
        ageGroupCounts.put("40", 0L);
        ageGroupCounts.put("50", 0L);
        ageGroupCounts.put("over_sixty", 0L);

        List<User> users = adminRepository.findAll();

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
                ageGroupCounts.put(ageGroup, ageGroupCounts.get(ageGroup) + adResEditRepository.countByUNum(user.getUNum()));
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
    public Map<String, Object> resumeEditCntByMode() {
        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, Object> editCounts = new LinkedHashMap<>();
        Map<String, Object> editRatios = new LinkedHashMap<>();

        // light모드 사용자 조회
        List<User> lightList = adminRepository.findByMode(1);
        long lightCnt = countResumeEdits(lightList);

        // pro모드 사용자 조회
        List<User> proList = adminRepository.findByMode(2);
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
    public Map<String, Object> resumeCntByMonth() {
        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, Integer> editCounts = new LinkedHashMap<>();
        Map<String, Double> editRatios = new LinkedHashMap<>();

        // 월별 첨삭 횟수 조회
        List<Object[]> monthList = adResRepository.findMonthlyCorrectionCounts();
        // 주차별 첨삭 횟수 조회
        List<Object[]> weeklyList = adResRepository.findWeeklyCorrectionCounts();
        // 일별 첨삭 횟수 조회
        List<Object[]> dailyList = adResRepository.findDailyCorrectionCounts();

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
            if (!weeklyEditCounts.containsKey(month)) {
                weeklyEditCounts.put(month, new LinkedHashMap<>());
                weeklyEditRatios.put(month, new LinkedHashMap<>());
            }

            Map<String, Integer> weekDataCounts = weeklyEditCounts.get(month);
            weekDataCounts.put(week, weeklyCnt);

            // 주차별 첨삭 비율 계산
            int monthTotalCnt = editCounts.get(month);
            double weeklyRatio = ((double) weeklyCnt / monthTotalCnt) * 100;
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
            if (!dailyEditCounts.containsKey(month)) {
                dailyEditCounts.put(month, new LinkedHashMap<>());
                dailyEditRatios.put(month, new LinkedHashMap<>());
            }

            Map<String, Integer> dayDataCounts = dailyEditCounts.get(month);
            dayDataCounts.put(dayKey, dailyCnt);

            // 일별 첨삭 비율 계산
            int monthTotalCnt = editCounts.get(month);
            double dailyRatio = ((double) dailyCnt / monthTotalCnt) * 100;
            Map<String, Double> dayDataRatios = dailyEditRatios.get(month);
            dayDataRatios.put(dayKey, Math.round(dailyRatio * 100) / 100.0);
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
    public Map<String, Object> resumeCntByWeekly() {
        Map<String, Object> result = new LinkedHashMap<>();

        // 일별 첨삭 횟수 조회
        List<Object[]> dailyList = adResRepository.findDailyCorrectionCounts();

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
    /*
    @Override
    public Map<String, Object> resumeCntByWeekly() {
        Map<String, Object> result = new LinkedHashMap<>();
        List<Object[]> weeklyList = adResRepository.findWeeklyCorrectionCounts();

        Map<String, Map<Integer, Integer>> monthlyData = new LinkedHashMap<>();
        Map<String, Integer> monthlyTotalCorrections = new LinkedHashMap<>();

        // 월별 총 첨삭 횟수 계산
        for (Object[] row : weeklyList) {
            String month = (String) row[0];
            int weeklyCnt = ((Number) row[2]).intValue();
            monthlyTotalCorrections.put(month, monthlyTotalCorrections.getOrDefault(month, 0) + weeklyCnt);
        }

        for (Object[] row : weeklyList) {
            String month = (String) row[0];
            int week = ((Number) row[1]).intValue();
            int weeklyCnt = ((Number) row[2]).intValue();

            // 월별 데이터가 없으면 초기화
            monthlyData.putIfAbsent(month, new LinkedHashMap<>());
            monthlyData.get(month).put(week, weeklyCnt);
        }

        for (Map.Entry<String, Map<Integer, Integer>> entry : monthlyData.entrySet()) {
            String month = entry.getKey();
            int totalCorrections = monthlyTotalCorrections.get(month);
            Map<String, Object> weeklyMap = new LinkedHashMap<>();

            for (Map.Entry<Integer, Integer> weekEntry : entry.getValue().entrySet()) {
                int week = weekEntry.getKey();
                int weeklyCnt = weekEntry.getValue();
                String weeklyRatio = String.format("%.2f%%", ((double) weeklyCnt / totalCorrections) * 100);

                weeklyMap.put(week + "주차 첨삭 횟수", weeklyCnt);
                weeklyMap.put(week + "주차 첨삭 비율", weeklyRatio);
            }
            result.put(month, weeklyMap);
        }

        return result;
    }
    */

    /* 채용시즌(일별) 첨삭 횟수 */
    @Override
    public Map<String, Object> resumeCntByDaily() {
        Map<String, Object> result = new LinkedHashMap<>();
        Map<String, Integer> editCounts = new LinkedHashMap<>();
        Map<String, Object> editRatios = new LinkedHashMap<>();

        List<Object[]> dailyList = adResRepository.findDailyCorrectionCounts();

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
