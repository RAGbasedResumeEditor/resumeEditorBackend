package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
//관리자 페이지 통계 데이터 처리해주는 클래스
// 유저 정보에 관한 통계 [그림1]
//총 회원수, 유저 연령대 및 성별 비율, 신입/경력 비율,
//N년차 비율, 현재 종사 직군 비율, 유저 희망 직군 비율, 프로/라이트 모드 비율(+각 모드의 재직 상태 비율)
//resume crud (resume_board table)

    private final UserRepository userRepository;

    @Scheduled(fixedDelay = 2000)
    @Override
    public void userCnt() { // User수를 집계하는 메서드
        List<User> users=userRepository.findAll();
        int cnt=users.size();
        //System.out.println("scheduler testing..."+cnt);
    }
}
