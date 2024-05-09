package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminRepository;
import com.team2.resumeeditorproject.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
//관리자 페이지 통계 데이터 처리해주는 클래스
/* 1. 유저 정보에 관한 통계
   현재 종사 직군(occupation) 비율, 유저 희망(wish) 직군 비율
 2. resume crud (resume_board table)
 */

    private final AdminRepository adminRepository;

    //@Scheduled(cron = "0 30 6,23 * * *") // 모든 요일 06:30AM, 23:30PM에 실행.
    @Scheduled(fixedDelay = 2000)
    @Override
    public void userCnt() {
        //총 회원수
        List<User> users=adminRepository.findAll();
        int cnt=users.size();
        //System.out.println(new Date()+" UserCount: "+cnt);

        //성비
        List<User> female = adminRepository.findByGender('F');
        List<User> male = adminRepository.findByGender('M');
        int userCnt=users.size();
        int femaleCnt=female.size();
        int maleCnt=male.size();
        String str1 = String.format("%.2f", ((double)femaleCnt/(double)userCnt)*100);
        String str2 = String.format("%.2f", ((double)maleCnt/(double)userCnt)*100);
        //System.out.println("여성 회원비: "+str1+" 남성 회원비: "+str2);

        //연령대
        List<User> twenties= adminRepository.findByAgeBetween(20, 29);
        List<User> thirties= adminRepository.findByAgeBetween(30, 39);
        List<User> fourties= adminRepository.findByAgeBetween(40, 49);
        List<User> fifties= adminRepository.findByAgeBetween(50, 59);
        List<User> overSixty= adminRepository.findByAgeBetween(60, 99);
        //System.out.println("20대 회원수: "+twenties.size());

        //신입 경력 비율
        List<User> unemployed=adminRepository.findByStatus(1); // 구직 중
        List<User> employed=adminRepository.findByStatus(2); // 이직 중
        String ratio1 = String.format("%.2f", ((double)unemployed.size()/(double)userCnt)*100); // 구직자 비율 => 변수명 변경 예정
        String ratio2 = String.format("%.2f", ((double)employed.size()/(double)userCnt)*100); // 이직자 비율
        //System.out.println("구직자 비율: "+ratio1+" 이직자 비율: "+ratio2);

        //프로/라이트 모드 비율(+각 모드의 재직 상태 비율)
        List<User> lightUser=adminRepository.findByMode(1); // 라이트 모드 유저
        List<User> proUser=adminRepository.findByMode(2); // 프로 모드 유저
        String ratio3 = String.format("%.2f", ((double)lightUser.size()/(double)userCnt)*100);
        String ratio4 = String.format("%.2f", ((double)proUser.size()/(double)userCnt)*100);
        //System.out.println("라이트 유저 비율: "+ratio3+" 프로 유저 비율: "+ratio4);
    }
}
