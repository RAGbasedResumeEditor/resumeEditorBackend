package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminResumeBoardRepository;
import com.team2.resumeeditorproject.admin.repository.AdminResumeEditRepository;
import com.team2.resumeeditorproject.admin.repository.AdminResumeRepository;
import com.team2.resumeeditorproject.admin.repository.AdminUserRepository;
import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import com.team2.resumeeditorproject.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

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

    //@Scheduled(cron = "0 30 6,23 * * *") // 모든 요일 06:30AM, 11:30PM에 실행.
    @Scheduled(fixedDelay = 2000)
    @Override
    public Map<String, Object> userCnt(){ // 총 회원수
        Map<String, Object> result=new HashMap<>();
        result.put("총 회원수", totalUserCnt(adminRepository)+"명");
        return result;
    }

    @Scheduled(fixedDelay = 2000) // 2초마다 실행(for test)
    @Override
    public Map<String, Object> genderCnt() {  //성비
        int userCnt = totalUserCnt(adminRepository);
        String female = String.format("%.2f", ((double) adminRepository.findByGender('F').size() / (double) userCnt) * 100);
        String male = String.format("%.2f", ((double)  adminRepository.findByGender('M').size() / (double) userCnt) * 100);
        Map<String, Object> result=new HashMap<>();
        result.put("여성",female+"%");
        result.put("남성",male+"%");
        return result;
    }

    @Override
    public Map<String,Object> occupCnt(String occupation) {  //occupation
        int userCnt = totalUserCnt(adminRepository);
        String occup = String.format("%.2f", ((double) adminRepository.findByOccupation(occupation).size() / (double) userCnt) * 100);
        Map<String,Object> result=new HashMap<>();
        result.put(occupation,occup+"%");
        return result;
    }

    @Override
    public  Map<String, Object> wishCnt(String wish) {
        int userCnt = totalUserCnt(adminRepository);
        String wishes = String.format("%.2f", ((double) adminRepository.findByWish(wish).size() / (double) userCnt) * 100);
        Map<String, Object> result=new HashMap<>();
        result.put(wish,wishes+"%");
        return result;
    }

    @Scheduled(fixedDelay = 2000) // 2초마다 실행(for test)
    @Override
    public Map<String, Integer> ageCnt() {  //연령대
        Map<String, Integer> result=new HashMap<>();
        for(int age=20;age<=50;age+=10){
            result.put(age+"대", adminRepository.findByAgeBetween(age, age+9).size());
        }
        result.put("60대 이상", adminRepository.findByAgeBetween(60, 99).size());
        return result;
    }

    @Scheduled(fixedDelay = 2000) // 2초마다 실행(for test)
    @Override
    public Map<String, Object> statusCnt() {   //신입 경력 비율
        int userCnt = totalUserCnt(adminRepository);
        String umeployed = String.format("%.2f", ((double)adminRepository.findByStatus(1).size()/(double)userCnt)*100); // 구직자 비율
        String employed = String.format("%.2f", ((double)adminRepository.findByStatus(2).size()/(double)userCnt)*100); // 이직자 비율
        Map<String, Object> result=new HashMap<>();
        result.put("구직자",umeployed+"%");
        result.put("이직자",employed+"%");
        return result;
    }

    @Scheduled(fixedDelay = 2000) // 2초마다 실행(for test)
    @Override
    public Map<String, Object> modeCnt() {    //프로 라이트 모드 비율
        int userCnt = totalUserCnt(adminRepository);
        String light = String.format("%.2f", ((double)adminRepository.findByMode(1).size()/(double)userCnt)*100);
        String pro = String.format("%.2f", ((double)adminRepository.findByMode(2).size()/(double)userCnt)*100);
        Map<String, Object> result=new HashMap<>();
        result.put("프로 유저 비율",light);
        result.put("라이트 유저 비율",pro);
        return result;
    }

    @Override
    public Map<String, Object> CompResumeCnt(String company) { // 회사별 자소서 평점, 조회수
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
        float avgRate=cnt > 0 ? sumRatesByCompany / cnt : 0;
        int avgViews = cnt > 0 ? sumViewsByComp / cnt : 0;

        result.put(company+" 자소서 개수",ratesByComp.size());
        result.put(company+" 자소서 평균 평점",avgRate);
        result.put(company+" 자소서 평균 조회수",avgViews);
        result.put("가장 높은 "+company+" 자소서 평점",ratesByComp.get(0));
        result.put("가장 낮은 "+company+" 자소서 평점",ratesByComp.get(cnt-1));
        result.put("가장 높은 "+company+" 자소서 조회수",viewsByComp.get(0));
        result.put("가장 낮은 "+company+" 자소서 조회수",viewsByComp.get(cnt-1));
        return result;
    }

    @Override
    public Map<String, Object> OccupResumeCnt(String occupation) {  //직군별 자소서 평점, 조회수
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

        result.put(occupation+" 자소서 개수",cnt);
        result.put(occupation+" 자소서 평균 평점",avgRate);
        result.put(occupation+" 자소서 평균 조회수",avgViews);
        result.put("가장 높은 "+occupation+" 자소서 평점",ratesByOccup.get(0));
        result.put("가장 낮은 "+occupation+" 자소서 평점",ratesByOccup.get(cnt-1));
        result.put("가장 높은 "+occupation+" 자소서 조회수",viewsByOccup.get(0));
        result.put("가장 낮은 "+occupation+" 자소서 조회수",viewsByOccup.get(cnt-1));
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

        // 신입(구직) 사용자 조회
        List<User> newUserList = adminRepository.findByStatus(1);
        long newWorkerCnt = countResumeEdits(newUserList);

        // 경력(이직) 사용자 조회
        List<User> experiencedUserList = adminRepository.findByStatus(2);
        long experiencedWorkerCnt = countResumeEdits(experiencedUserList);

        long totalEditCnt = newWorkerCnt + experiencedWorkerCnt;
        String newWorkerRatio = String.format("%.2f",((double) newWorkerCnt / totalEditCnt) * 100);
        String experiencedWorkerRatio = String.format("%.2f",((double) experiencedWorkerCnt / totalEditCnt) * 100);

        result.put("신입 첨삭 횟수", newWorkerCnt);
        result.put("경력 첨삭 횟수", experiencedWorkerCnt);

        result.put("신입 첨삭 비율", newWorkerRatio +"%");
        result.put("경력 첨삭 비율", experiencedWorkerRatio +"%");

        return result;
    }

    /* 직군 별 첨삭 횟수,비율 */
    @Override
    public Map<String, Object> resumeEditCntByOccup(String occupation) {
        Map<String, Object> result = new LinkedHashMap<>();

        int userCnt = totalUserCnt(adminRepository);
        List<User> occupList = adminRepository.findByOccupation(occupation);

        long occupCnt = countResumeEdits(occupList);
        String occupRatio = String.format("%.2f",((double) occupCnt / userCnt)*100);

        result.put(occupation +" 직군의 첨삭 횟수", occupCnt);
        result.put(occupation +" 직군의 첨삭 비율", occupRatio +"%");

        return result;
    }

    /* 회사 별 첨삭 횟수,비율*/
    @Override
    public Map<String, Object> resumeEditCntByComp(String company) {
        Map<String, Object> result = new LinkedHashMap<>();

        int userCnt = totalUserCnt(adminRepository);
        List<User> compList = adminRepository.findByCompany(company);

        long compCnt = countResumeEdits(compList);
        String compRatio = String.format("%.2f",((double) compCnt / userCnt) * 100);

        result.put("회사명[" + company +"] 첨삭 횟수", compCnt);
        result.put("회사명[" + company +"] 첨삭 비율", compRatio +"%");

        return result;
    }

    /* 연령 별 첨삭 횟수,비율 */
    @Override
    public Map<String, Object> resumeEditCntByAge() {
        Map<String, Long> ageGroupCounts = new LinkedHashMap<>();

        ageGroupCounts.put("20대", 0L);
        ageGroupCounts.put("30대", 0L);
        ageGroupCounts.put("40대", 0L);
        ageGroupCounts.put("50대", 0L);
        ageGroupCounts.put("60대 이상", 0L);

        List<User> users = adminRepository.findAll();

        for (User user : users) {
            int age = user.getAge();
            if (age >= 20 && age < 30) {
                ageGroupCounts.put("20대", ageGroupCounts.get("20대") + adResEditRepository.countByUNum(user.getUNum()));
            } else if (age >= 30 && age < 40) {
                ageGroupCounts.put("30대", ageGroupCounts.get("30대") + adResEditRepository.countByUNum(user.getUNum()));
            } else if (age >= 40 && age < 50) {
                ageGroupCounts.put("40대", ageGroupCounts.get("40대") + adResEditRepository.countByUNum(user.getUNum()));
            } else if (age >= 50 && age < 60) {
                ageGroupCounts.put("50대", ageGroupCounts.get("50대") + adResEditRepository.countByUNum(user.getUNum()));
            } else if (age >= 60) {
                ageGroupCounts.put("60대 이상", ageGroupCounts.get("60대 이상") + adResEditRepository.countByUNum(user.getUNum()));
            }
        }

        long totalEdits = ageGroupCounts.values().stream().mapToLong(Long::longValue).sum();
        Map<String, Object> result = new HashMap<>();

        Map<String, Double> ageGroupRatios = new HashMap<>();
        for (String ageGroup : ageGroupCounts.keySet()) {
            long count = ageGroupCounts.get(ageGroup);
            ageGroupRatios.put(ageGroup, totalEdits == 0 ? 0.0 : (double) count / totalEdits * 100);
        }

        Map<String, String> formattedAgeGroupRatios = new HashMap<>();
        for (Map.Entry<String, Double> entry : ageGroupRatios.entrySet()) {
            formattedAgeGroupRatios.put(entry.getKey(), String.format("%.2f%%", entry.getValue()));
        }

        result.put("연령별 첨삭 횟수", ageGroupCounts);
        result.put("연령별 첨삭 비율", formattedAgeGroupRatios);

        return result;
    }

    /* 모드 별 첨삭 횟수,비율*/
    @Override
    public Map<String, Object> resumeEditCntByMode() {
        Map<String, Object> result = new LinkedHashMap<>();

        // light모드 사용자 조회
        List<User> lightList = adminRepository.findByMode(1);
        long lightCnt = countResumeEdits(lightList);

        // pro모드 사용자 조회
        List<User> proList = adminRepository.findByMode(2);
        long proCnt = countResumeEdits(proList);

        long totalEditCnt = lightCnt + proCnt;
        String lightRatio = String.format("%.2f",((double) lightCnt / totalEditCnt) * 100);
        String proRatio = String.format("%.2f",((double) proCnt / totalEditCnt) * 100);

        result.put("light 첨삭 횟수", lightCnt);
        result.put("pro 첨삭 횟수", proCnt);

        result.put("light 첨삭 비율", lightRatio +"%");
        result.put("pro 첨삭 비율", proRatio +"%");

        return result;
    }

    /* 채용시즌(월 별) 첨삭 횟수 */
    @Override
    public Map<String, Object> resumeCntByMonth() {
        Map<String, Object> result = new LinkedHashMap<>();
        List<Object[]> monthList = adResRepository.findMonthlyCorrectionCounts();

        int totalCorrections = monthList.stream().mapToInt(row -> ((Number) row[1]).intValue()).sum();

        for(Object[] row : monthList){
            String month =(String) row[0];
            int monthCnt = ((Number) row[1]).intValue();
            String monthRatio = String.format("%.2f", ((double) monthCnt / totalCorrections) * 100);

            Map<String, Object> monthlyStats = new HashMap<>();
            monthlyStats.put("월 별 첨삭 횟수", monthCnt);
            monthlyStats.put("월 별 첨삭 비율", monthRatio+"%");

            result.put(month, monthlyStats);
        }

        return result;
    }

    /* 채용시즌(주차 별) 첨삭 횟수 */
    @Override
    public Map<String, Object> resumeCntByWeekly() {
        Map<String, Object> result = new LinkedHashMap<>();
        List<Object[]> weeklyList = adResRepository.findWeeklyCorrectionCounts();

        Map<String, Map<Integer, Integer>> monthlyData = new LinkedHashMap<>();

        //int totalCorrections = weeklyList.stream().mapToInt(row -> ((Number) row[1]).intValue()).sum();

        for(Object[] row : weeklyList){
            String month = (String) row[0];
            int week =((Number) row[1]).intValue();
            int weeklyCnt = ((Number) row[2]).intValue();
            //String weeklyRatio = String.format("%.2f", ((double) weeklyCnt / totalCorrections) * 100);

            // 월별 데이터가 없으면 초기화
            monthlyData.putIfAbsent(month, new LinkedHashMap<>());
            monthlyData.get(month).put(week, weeklyCnt);

        }
        for (Map.Entry<String, Map<Integer, Integer>> entry : monthlyData.entrySet()) {
            Map<String, Object> weeklyMap = new LinkedHashMap<>();
            for (Map.Entry<Integer, Integer> weekEntry : entry.getValue().entrySet()) {
                weeklyMap.put(weekEntry.getKey() + "주차 첨삭 횟수", weekEntry.getValue());
            }
            result.put(entry.getKey(), weeklyMap);
        }

        return result;
    }

    /* 채용시즌(일 별) 첨삭 횟수 */
    @Override
    public Map<String, Object> resumeCntByDaily() {
        Map<String, Object> result = new LinkedHashMap<>();
        List<Object[]> dailyList = adResRepository.findDailyCorrectionCounts();

        int totalCorrections = dailyList.stream().mapToInt(row -> ((Number) row[1]).intValue()).sum();

        for(Object[] row : dailyList){
            String day =(String) row[0];
            int dayCnt = ((Number) row[1]).intValue();
            String dayRatio = String.format("%.2f", ((double) dayCnt / totalCorrections) * 100);

            Map<String, Object> dailyStats = new HashMap<>();
            dailyStats.put("일자 별 첨삭 횟수", dayCnt);
            dailyStats.put("일자 별 첨삭 비율", dayRatio+"%");

            result.put(day, dailyStats);
        }

        return result;
    }
}
