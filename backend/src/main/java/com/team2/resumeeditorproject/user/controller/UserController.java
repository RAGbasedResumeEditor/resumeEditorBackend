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

    @PostMapping(value="/signup")
<<<<<<< HEAD
    public ResponseEntity<Map<String,Object>> signup(UserDTO userDto, HttpServletResponse res) throws IOException {

=======
    public ResponseEntity<Map<String,Object>> signup(@RequestBody UserDTO userDto, HttpServletResponse res) throws IOException {

        //System.out.println("UserDto: "+userDto.toString());
>>>>>>> 8b97b5ac7d556187da118a8fe88d7f4326108f33
        Map<String,Object> response=new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();

        try{ //회원가입 성공 시
            Long result= userService.signup(userDto);
            response.put("status","Success"); //1. 상태(실패)
            response.put("time", new Date()); //2. 시간
            response.put("response","회원가입 성공"); //3. 메시지 내용(성공)
            return ResponseEntity.ok(response);
        }catch(Exception e){ // 회원가입 실패 시
            errorResponse.put("status","Fail"); //1. 상태(실패)
            errorResponse.put("time",new Date()); //2. 시간
            errorResponse.put("response", "서버 오류입니다."); //3. 메시지 내용(에러)

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }//signup()

    @PostMapping("/signup/exists/email")
    public ResponseEntity<Map<String,Object>> checkEmailDuplicate(@RequestParam("email") String email){
        Map<String,Object> response=new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        try{
            boolean result= userService.checkEmailDuplicate(email);
            //System.out.println("email check result (중복 시 true): "+result);
            response.put("status","Success");
            response.put("result",result+"");
            response.put("time", new Date());
            response.put("response", "이메일 중복 여부 확인 성공");
            return ResponseEntity.ok(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/signup/exists/username")
<<<<<<< HEAD
    public ResponseEntity<Map<String,Object>> checkNicknameDuplicate(@RequestParam("username") String username){
=======
    public ResponseEntity<Map<String,Object>> checkUsernameDuplicate(@RequestParam("username") String username){
>>>>>>> 8b97b5ac7d556187da118a8fe88d7f4326108f33
            Map<String,Object> response=new HashMap<>();
            Map<String,Object> errorResponse=new HashMap<>();
            try{
                boolean result=userService.checkUsernameDuplicate(username); // 중복 시 true
                response.put("status","Success");
                response.put("result",result+"");
                response.put("time", new Date());
                response.put("response", "Username 중복 여부 확인 성공");
                return ResponseEntity.ok(response);
            }catch(Exception e){
                errorResponse.put("status","Fail");
                errorResponse.put("time",new Date());
                errorResponse.put("response", "서버 오류입니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
    }
}
