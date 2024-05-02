package com.team2.resumeeditorproject.user.controller;

import com.team2.resumeeditorproject.user.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/mailSend") // 사용자에게 이메일을 보낸다.
    public ResponseEntity<Map<String, String>> mailSend(@RequestParam("email") String email){
        //System.out.println("이메일 인증 요청이 들어왔습니다. 인증을 요청한 이메일은 "+email+"입니다.");
        Map<String, String> response=new HashMap<>();
        Map<String, String> errorResponse=new HashMap<>();
        try { // 이메일 전송 성공 시
            mailService.joinEmail(email);
            response.put("status","Success");
            response.put("time", String.valueOf(new Date()));
            response.put("response","인증코드 이메일 전송 성공");
            return ResponseEntity.ok(response);
        }catch(Exception e){ // 이메일 전송 실패 시
            errorResponse.put("status","Fail");
            errorResponse.put("time",String.valueOf(new Date()));
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }//MailSend()

    @PostMapping("/mailAuthCheck")
    public ResponseEntity<Map<String, String>> authCheck(@RequestParam("email") String email, @RequestParam("authCode") String authCode){
        boolean checked=mailService.CheckAuthNum(email, authCode); // Redis 일치 여부 확인

        Map<String, String> response=new HashMap<>();
        Map<String, String> errorResponse=new HashMap<>();
        if(checked){
            response.put("status", "Success");
            response.put("time", String.valueOf(new Date()));
            response.put("response","회원가입 이메일 인증 성공");
            return ResponseEntity.ok(response);
        } else{
            errorResponse.put("status","Fail");
            errorResponse.put("time",String.valueOf(new Date()));
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}