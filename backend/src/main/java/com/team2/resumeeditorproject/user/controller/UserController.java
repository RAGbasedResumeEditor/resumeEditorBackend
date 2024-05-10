package com.team2.resumeeditorproject.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.service.UserService;
import jakarta.servlet.ServletInputStream;
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
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController extends HttpServlet {

    private final UserService userService;

    //회원가입
    @PostMapping(value="/signup")
    public ResponseEntity<Map<String,Object>> signup(@RequestBody UserDTO userDto) throws IOException {
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

    @PostMapping("/signup/exists/username")
    public ResponseEntity<Map<String,Object>> checkUsernameDuplicate(HttpServletRequest req) throws AuthenticationException {
             UserDTO userDto=new UserDTO();
            try{
                ObjectMapper objectMapper=new ObjectMapper();
                ServletInputStream inputStream=req.getInputStream();
                String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
                userDto=objectMapper.readValue(messageBody, UserDTO.class);
            }catch(IOException e){
                throw new RuntimeException(e);
            }
            String username=userDto.getUsername();
        
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
    /*
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
  */

    //회원탈퇴
    @PostMapping("/user/withdraw")
    public ResponseEntity<Map<String, Object>> withdraw(HttpServletRequest req) throws AuthenticationException{
        UserDTO userDto=new UserDTO();
        try{
            ObjectMapper objectMapper=new ObjectMapper();
            ServletInputStream inputStream=req.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            userDto=objectMapper.readValue(messageBody, UserDTO.class);
        }catch(IOException e){
            throw new RuntimeException(e);
        }

        Long uNum=userDto.getUNum();

        Map<String,Object> response=new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();

        //유효성 검사
        if(userDto.getUsername()==null){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "존재하지 않는 회원입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
        //탈퇴처리
        userService.withdraw(uNum);
        response.put("status","Success");
        response.put("time", new Date());
        response.put("response", "회원탈퇴 완료.");
        return ResponseEntity.ok(response);
    }

    //회원정보 수정
    @PostMapping("/user/edit")
    public ResponseEntity<Map<String, Object>> edit(HttpServletRequest req) throws AuthenticationException{
        UserDTO userDto=new UserDTO();
        try{
            ObjectMapper objectMapper=new ObjectMapper();
            ServletInputStream inputStream=req.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            userDto=objectMapper.readValue(messageBody, UserDTO.class);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        Map<String,Object> response=new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();

        //유효성 검사
        if(userDto.getUsername()==null){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "존재하지 않는 회원입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

        //수정 처리
        userService.userUpdate(userDto);
        response.put("status","Success");
        response.put("time", new Date());
        response.put("response", "회원정보 수정 완료.");
        return ResponseEntity.ok(response);
    }
}
