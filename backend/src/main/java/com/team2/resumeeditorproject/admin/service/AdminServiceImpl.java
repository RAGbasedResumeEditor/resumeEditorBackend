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
