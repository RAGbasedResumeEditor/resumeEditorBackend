package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserService {
    // 회원가입
    Long signup(UserDTO userDto);
    //Boolean checkEmailDuplicate(String email);
    Boolean checkUsernameDuplicate(String username);

    /* eunbi */
    int updateUserMode(long u_num);

    //회원탈퇴
    void deleteUser(Long uNum);
    @Transactional
    @Scheduled(cron = "0 0 12 * * *") // 매일 오후 12시에 메서드 동작
    void deleteUserEnd();
    //회원 비밀번호 수정
    void updateUserPw(UserDTO userDto);
    //회원정보 수정
    @Transactional
    void updateUser(UserDTO userDto);
}
