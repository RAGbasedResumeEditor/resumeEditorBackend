package com.team2.resumeeditorproject.user.controller;

import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.service.UserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.hibernate.service.spi.InjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
public class UserController extends HttpServlet {

    private final UserService userService;

    @GetMapping(value = "/signup")
    public String signupForm(UserDTO userDto){
            return "signup";
        }

    @PostMapping(value="/signup")
    public ResponseEntity<Map<String,String>> signup(@RequestBody UserDTO userDto, HttpServletResponse res) throws IOException {

        //System.out.println("UserDto: "+userDto.toString());

        Map<String,String> response=new HashMap<>();
        Map<String,String> errorResponse=new HashMap<>();

        try{ //회원가입 성공 시
            Long result= userService.signup(userDto);
            //System.out.println("signup result: "+result);
            response.put("status","Success"); //1. 상태(실패)
            response.put("time", String.valueOf(new Date())); //2. 시간
            response.put("response","회원가입 성공"); //3. 메시지 내용(성공)
            //res.sendRedirect("http://localhost:8088/login");
            return ResponseEntity.ok(response);
        }catch(Exception e){ // 회원가입 실패 시
            errorResponse.put("status","Fail"); //1. 상태(실패)
            errorResponse.put("time",String.valueOf(new Date())); //2. 시간
            errorResponse.put("response", "서버 오류입니다."); //3. 메시지 내용(에러)

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }//signup()

    @PostMapping("/signup/email/exists")
    public ResponseEntity<Map<String,String>> checkEmailDuplicate(@RequestParam("email") String email){
        Map<String,String> response=new HashMap<>();
        Map<String,String> errorResponse=new HashMap<>();
        try{
            boolean result= userService.checkEmailDuplicate(email);
            //System.out.println("email check result (중복 시 true): "+result);
            response.put("status","Success");
            response.put("result",result+"");
            response.put("time", String.valueOf(new Date()));
            response.put("response", "이메일 중복 여부 확인 성공");
            return ResponseEntity.ok(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",String.valueOf(new Date()));
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
