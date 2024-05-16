package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminResumeBoardRepository;
import com.team2.resumeeditorproject.admin.repository.AdminResumeEditRepository;
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
/*
      3) 자소서 첨삭 이용 통계
        채용 시즌(날짜) 별 첨삭 횟수, 신입/경력 별 첨삭 횟수, 후기 별점 신입/경력별, 직군별, 연령별, 프로/라이트별 비율.
 */
    private final AdminUserRepository adminRepository;
    private final AdminResumeEditRepository adResEditRepository;
    private final AdminResumeBoardRepository adResBoardRepository;

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

    private long countResumeEdits(List<User> userList) {
        long totalResumeEdits = 0;
        for (User user : userList) {
            totalResumeEdits += adResEditRepository.countByUNum(user.getUNum());
        }
        return totalResumeEdits;
    }

    /* 신입/경력 별 첨삭 횟수 */
    @Override
    public Map<String, Long> resumeEditCntByStatus() {
        Map<String, Long> result = new HashMap<>();

        // 상태가 1인 사용자들을 조회
        List<User> newUserList = adminRepository.findByStatus(1);
        long newWorkerCnt = countResumeEdits(newUserList);

        // 상태가 2인 사용자들을 조회
        List<User> experiencedUserList = adminRepository.findByStatus(2);
        long experiencedWorkerCnt = countResumeEdits(experiencedUserList);

        result.put("신입 첨삭 횟수", newWorkerCnt);
        result.put("경력 첨삭 횟수", experiencedWorkerCnt);

        return result;
    }
}
