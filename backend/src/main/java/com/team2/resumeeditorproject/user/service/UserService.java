package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public interface UserService {
    // 회원가입
    Long signup(UserDTO userDto);
    //Boolean checkEmailDuplicate(String email);
    Boolean checkUsernameDuplicate(String username);
    //회원탈퇴
    void withdraw(Long uNum);
    //회원정보 수정
    @Transactional
    void userUpdate(UserDTO userDto);
}
