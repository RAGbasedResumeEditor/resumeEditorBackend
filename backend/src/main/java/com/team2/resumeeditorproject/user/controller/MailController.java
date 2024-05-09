package com.team2.resumeeditorproject.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.service.MailService;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/signup")
public class MailController {

    private final MailService mailService;

    @PostMapping("/auth-code") // 사용자에게 이메일을 보낸다.
    public ResponseEntity<Map<String, Object>> mailSend(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        UserDTO userDto=new UserDTO();
        try{
            ObjectMapper objectMapper=new ObjectMapper();
            ServletInputStream inputStream=req.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            userDto=objectMapper.readValue(messageBody, UserDTO.class);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        String email=userDto.getEmail();
        Map<String, Object> response=new HashMap<>();
        Map<String, Object> errorResponse=new HashMap<>();
        try { // 이메일 전송 성공 시
            mailService.joinEmail(email);
            response.put("status","Success");
            response.put("time", new Date());
            response.put("response","인증코드 이메일 전송 성공");
            return ResponseEntity.ok(response);
        }catch(Exception e){ // 이메일 전송 실패 시
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }//MailSend()

    @PostMapping("/auth-check")
    public ResponseEntity<Map<String, Object>> authCheck(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException{
        UserDTO userDto=new UserDTO();
        try{
            ObjectMapper objectMapper=new ObjectMapper();
            ServletInputStream inputStream=req.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            userDto=objectMapper.readValue(messageBody, UserDTO.class);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        String email=userDto.getEmail();
        String authCode=userDto.getAuthCode();

        boolean checked=mailService.CheckAuthNum(email, authCode); // Redis 일치 여부 확인

        Map<String, Object> response=new HashMap<>();
        Map<String, Object> errorResponse=new HashMap<>();

        if(checked){
            response.put("status", "Success");
            response.put("time", new Date());
            response.put("response","회원가입 이메일 인증 성공");
            return ResponseEntity.ok(response);
        }else if(!checked){
            response.put("status", "Success");
            response.put("time", new Date());
            response.put("response","회원가입 이메일 인증 성공 실패. 입력하신 이메일 주소 혹은 인증코드를 확인해주세요.");
            return ResponseEntity.ok(response);
        }else{
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}