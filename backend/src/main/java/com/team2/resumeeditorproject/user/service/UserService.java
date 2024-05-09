package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.user.dto.UserDTO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public interface UserService {
    Long signup(UserDTO userDto); // 회원가입
    //Boolean checkEmailDuplicate(String email);
    Boolean checkUsernameDuplicate(String username);
}
