package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminRepository;
import com.team2.resumeeditorproject.admin.repository.AdminResumeBoardRepository;
import com.team2.resumeeditorproject.admin.repository.AdminResumeEditRepository;
import com.team2.resumeeditorproject.resume.domain.Resume;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import com.team2.resumeeditorproject.resume.dto.ResumeEditDTO;
import com.team2.resumeeditorproject.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
//관리자 페이지 통계 데이터 처리해주는 클래스
/* 1. 1)유저 정보에 관한 통계 => userCnt()
        현재 종사 직군(occupation) 비율, 유저 희망(wish) 직군 비율
      2)자소서 목록(resume_board)에 관한 통계 => resumeList()
        회사별 직군별 재직상태별 희망직군별 평균 평점 순위,  회사별 직군별 재직상태별 희망직군별  자소서 조회수 순위(TOP 10)
      3) 자소서 첨삭 이용 통계
        채용 시즌(날짜) 별 첨삭 횟수, 신입/경력 별 첨삭 횟수, 후기 별점 신입/경력별, 직군별, 연령별, 프로/라이트별 비율.
   2. resume crud (resume_board table)
 */

    private final AdminRepository adminRepository;
    private final AdminResumeEditRepository adResEditRepository;
    private final AdminResumeBoardRepository adResBoardRepository;

    //@Scheduled(cron = "0 30 6,23 * * *") // 모든 요일 06:30AM, 11:30PM에 실행.
    @Scheduled(fixedDelay = 2000) // 2초마다 실행(for test)
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
        //System.out.println("구직자 비율: "+ratio1+"% 이직자 비율: "+ratio2+"%");

        //프로 라이트 모드 비율
        List<User> lightUser=adminRepository.findByMode(1); // 라이트 모드 유저
        List<User> proUser=adminRepository.findByMode(2); // 프로 모드 유저
        String ratio3 = String.format("%.2f", ((double)lightUser.size()/(double)userCnt)*100);
        String ratio4 = String.format("%.2f", ((double)proUser.size()/(double)userCnt)*100);
        //System.out.println("라이트 유저 비율: "+ratio3+" 프로 유저 비율: "+ratio4);
    }

    @Scheduled(fixedDelay = 2000)
    @Override
    public void resumeList(){
        //회사별 평균 평점 TOP5 resumeBoard resumeEdit rnum으로 조인해서 company에 해당하는 rating가져와야 한다.
        List<ResumeEdit> lg=adResEditRepository.findByCompany("LG"); // 회사가 LG인 ResumeEdit 엔티티를 찾는다.
        List<Long> rNum=new ArrayList<>();
        float ratings=0;
            for(int i=0;i<lg.size();i++){
                rNum.add(lg.get(i).getR_num());
                System.out.println(rNum.get(i));
            }
          /* for(int i=0;i<rNum.size();i++){
                ratings+=adResBoardRepository.findById(rNum.get(i)).get().getRating();
            } //찾은 rnum값을 가진 엔티티를 ResumeBoard에서 찾는다. 그 엔티티의 rating값을 꺼내서 합을 구해준다.
            ratings/=rNum.size();
        System.out.println("평균 평점: "+ratings);*/
    }
}
