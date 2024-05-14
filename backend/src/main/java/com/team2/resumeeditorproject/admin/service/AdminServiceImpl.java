package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminResumeBoardRepository;
import com.team2.resumeeditorproject.admin.repository.AdminResumeEditRepository;
import com.team2.resumeeditorproject.admin.repository.AdminUserRepository;
import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import com.team2.resumeeditorproject.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //@Scheduled(cron = "0 30 6,23 * * *") // 모든 요일 06:30AM, 11:30PM에 실행.
    //@Scheduled(fixedDelay = 2000)
    @Override
    public Map<String, Object> userCnt(){ // 총 회원수
        List<User> users=adminRepository.findAll();
        Map<String, Object> userCnt=new HashMap<>();
        userCnt.put("총 회원수", users.size()+"명");
        return userCnt;
    }

    @Scheduled(fixedDelay = 2000) // 2초마다 실행(for test)
    @Override
    public Map<String, Object> genderCnt() {  //성비
        List<User> users = adminRepository.findAll();
        int userCnt = users.size();
        List<User> female = adminRepository.findByGender('F');
        List<User> male = adminRepository.findByGender('M');
        int femaleCnt = female.size();
        int maleCnt = male.size();
        String str1 = String.format("%.2f", ((double) femaleCnt / (double) userCnt) * 100);
        String str2 = String.format("%.2f", ((double) maleCnt / (double) userCnt) * 100);
        Map<String, Object> result=new HashMap<>();
        result.put("여성",str1+"%");
        result.put("남성",str2+"%");
        return result;
    }

    @Override
    public Map<String,Object> occupCnt(String occupation) {  //occupation
        List<User> users = adminRepository.findAll();
        int userCnt = users.size();
        List<User> occp = adminRepository.findByOccupation(occupation);
        int devCnt = occp.size();
        String str = String.format("%.2f", ((double) devCnt / (double) userCnt) * 100);
        Map<String,Object> occupCnt=new HashMap<>();
        occupCnt.put(occupation,str+"%");
        return occupCnt;
    }

    @Override
    public  Map<String, Object> wishCnt(String wish) {
        List<User> users = adminRepository.findAll();
        int userCnt = users.size();
        List<User> wishes = adminRepository.findByWish(wish);
        int WishCnt = wishes.size();
        String str = String.format("%.2f", ((double) WishCnt / (double) userCnt) * 100);
        Map<String, Object> wishCnt=new HashMap<>();
        wishCnt.put(wish,str+"%");
        return wishCnt;
    }

    @Scheduled(fixedDelay = 2000) // 2초마다 실행(for test)
    @Override
    public Map<String, Integer> ageCnt() {  //연령대
        Map<String, Integer> userAge=new HashMap<>();
        for(int age=20;age<=50;age+=10){
            userAge.put(age+"대", adminRepository.findByAgeBetween(age, age+9).size());
        }
        userAge.put("60대 이상", adminRepository.findByAgeBetween(60, 99).size());
        return userAge;
    }

    @Scheduled(fixedDelay = 2000) // 2초마다 실행(for test)
    @Override
    public Map<String, Object> statusCnt() {   //신입 경력 비율
        List<User> users = adminRepository.findAll();
        int userCnt = users.size();
        List<User> unemployed=adminRepository.findByStatus(1); // 구직 중
        List<User> employed=adminRepository.findByStatus(2); // 이직 중
        String ratio1 = String.format("%.2f", ((double)unemployed.size()/(double)userCnt)*100); // 구직자 비율
        String ratio2 = String.format("%.2f", ((double)employed.size()/(double)userCnt)*100); // 이직자 비율
        Map<String, Object> statusCnt=new HashMap<>();
        statusCnt.put("구직자",ratio1+"%");
        statusCnt.put("이직자",ratio2+"%");
        return statusCnt;
    }

    @Scheduled(fixedDelay = 2000) // 2초마다 실행(for test)
    @Override
    public Map<String, Object> modeCnt() {    //프로 라이트 모드 비율
        List<User> users = adminRepository.findAll();
        int userCnt = users.size();
        List<User> lightUser=adminRepository.findByMode(1); // 라이트 모드 유저
        List<User> proUser=adminRepository.findByMode(2); // 프로 모드 유저
        String ratio1 = String.format("%.2f", ((double)lightUser.size()/(double)userCnt)*100);
        String ratio2 = String.format("%.2f", ((double)proUser.size()/(double)userCnt)*100);
        Map<String, Object> modeCnt=new HashMap<>();
        modeCnt.put("프로 유저 비율",ratio1);
        modeCnt.put("라이트 유저 비율",ratio2);
        return modeCnt;
    }

    @Override
    public Map<String, Object> CompResumeCnt(String company) { // 회사별 자소서 평점, 조회수
        List<ResumeEdit> resumeByCompany = adResEditRepository.findByCompany(company);
        List<Long> rNumByComp = new ArrayList<>();
        List<Float> ratesByComp = new ArrayList<>();
        List<Integer> viewsByComp = new ArrayList<>();
        for(int i = 0; i < resumeByCompany.size(); i++) {
            rNumByComp.add(resumeByCompany.get(i).getR_num());
        }
        for(int i = 0; i < rNumByComp.size(); i++) {//찾은 rnum값을 가진 엔티티를 ResumeBoard에서 찾는다. 그 엔티티의 rating, read_num값을 꺼내서 리스트에 저장한다.
            Long rn = rNumByComp.get(i);
            if (adResBoardRepository.findById(rn).isPresent()) {
                ratesByComp.add(adResBoardRepository.findById(rn).get().getRating());
                viewsByComp.add(adResBoardRepository.findById(rn).get().getRead_num());
            }
        }
        Map<String, Object> resumeByComp=new HashMap<>();
        resumeByComp.put(company+" 자소서 평점",ratesByComp);
        resumeByComp.put(company+" 자소서 조회수",ratesByComp);
        return resumeByComp;
    }

    @Override
    public Map<String, Object> OccupResumeCnt(String occupation) {  //직군별 자소서 평점, 조회수
        List<ResumeEdit> resumeByOccupation=adResEditRepository.findByOccupation(occupation);
        List<Long> rNumByOccup=new ArrayList<>();
        List<Float> ratesByOccup=new ArrayList<>();
        List<Integer> viewsByOccup=new ArrayList<>();
            for(int i=0;i<resumeByOccupation.size();i++){
                rNumByOccup.add(resumeByOccupation.get(i).getR_num());
            }
            for(int i=0;i<rNumByOccup.size();i++){
                Long rn= rNumByOccup.get(i);
                if(adResBoardRepository.findById(rn).isPresent()){
                    ratesByOccup.add(adResBoardRepository.findById(rn).get().getRating());
                    viewsByOccup.add(adResBoardRepository.findById(rn).get().getRead_num());
                }
            }
        Map<String, Object> resumeByComp=new HashMap<>();
        resumeByComp.put(occupation+" 자소서 평점",ratesByOccup);
        resumeByComp.put(occupation+" 자소서 조회수",viewsByOccup);
        return resumeByComp;
    }
}
