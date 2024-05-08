package com.team2.resumeeditorproject.user.controller;

import com.team2.resumeeditorproject.user.dto.UserDTO;
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

    @PostMapping("/auth-code") // 사용자에게 이메일을 보낸다.
    public ResponseEntity<Map<String, Object>> mailSend(@RequestParam("email") String email){
<<<<<<< HEAD
=======
        System.out.println("이메일 인증 요청이 들어왔습니다. 인증을 요청한 이메일은 "+email+"입니다.");
>>>>>>> 8b97b5ac7d556187da118a8fe88d7f4326108f33
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
<<<<<<< HEAD
    public ResponseEntity<Map<String, Object>> authCheck(@RequestParam("email") String email, @RequestParam("authCode") String authCode){
        boolean checked=mailService.CheckAuthNum(email, authCode); // Redis 일치 여부 확인
        Map<String, Object> response=new HashMap<>();
        Map<String, Object> errorResponse=new HashMap<>();
        if(checked){
            response.put("status", "Success");
            response.put("time", new Date());
            response.put("response","회원가입 이메일 인증 성공");
            return ResponseEntity.ok(response);
        } else{
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
=======
            public ResponseEntity<Map<String, Object>> authCheck(@RequestParam("email") String email, @RequestParam("authCode") String authCode){
                boolean checked=mailService.CheckAuthNum(email, authCode); // Redis 일치 여부 확인. true인 경우 인증 성공.
                Map<String, Object> response=new HashMap<>();
                Map<String, Object> errorResponse=new HashMap<>();
                if(checked){
                    response.put("status", "Success");
                    response.put("time", new Date());
                    response.put("response","회원가입 이메일 인증 성공");
                    return ResponseEntity.ok(response);
                }else if(!checked){
                    response.put("status","Success");
                    response.put("time",new Date());
                    response.put("response", "회원가입 이메일 인증 실패. 입력 이메일 혹은 인증 코드를 확인해주세요.");
                    return ResponseEntity.ok(response);
                }else{
                    errorResponse.put("status","Fail");
                    errorResponse.put("time",String.valueOf(new Date()));
                    errorResponse.put("response", "서버 오류입니다.");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
                }
>>>>>>> 8b97b5ac7d556187da118a8fe88d7f4326108f33
    }
}