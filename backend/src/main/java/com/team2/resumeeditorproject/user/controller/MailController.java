package com.team2.resumeeditorproject.user.controller;

import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/signup")
public class MailController {

    private final MailService mailService;

    @PostMapping("/auth-code") // 사용자에게 이메일을 보낸다.
    public ResponseEntity<Map<String, Object>> mailSend(@RequestBody UserDTO userDto) throws AuthenticationException {
        String email=userDto.getEmail();
        Map<String, Object> response=new HashMap<>();
        Map<String, Object> errorResponse=new HashMap<>();
        try {
            mailService.sendEmail(email);
            response.put("status","Success");
            response.put("response","인증 코드 전송 성공");
            return ResponseEntity.ok(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @PostMapping("/auth-check")
    public ResponseEntity<Map<String, Object>> authCheck(@RequestBody UserDTO userDto) throws AuthenticationException{

        String email=userDto.getEmail();
        String authCode=userDto.getAuthCode();

        boolean checked=mailService.checkAuthNum(email, authCode); // Redis 일치 여부 확인

        Map<String, Object> response=new HashMap<>();
        Map<String, Object> errorResponse=new HashMap<>();

        try {
            if (checked) {
                response.put("status", "Success");
                response.put("response", "인증 성공");
                return ResponseEntity.ok(response);
            } else if (!checked) {
                errorResponse.put("status", "Fail");
                errorResponse.put("time", new Date());
                errorResponse.put("response", "인증 실패. 입력한 이메일 주소 혹은 인증 코드를 확인해주세요.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
        }catch(Exception e){
            errorResponse.put("status", "Fail");
            errorResponse.put("time", new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
        return null;
    }
}