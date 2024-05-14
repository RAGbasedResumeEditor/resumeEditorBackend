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
        try{ //회원가입 처리
            if(userService.checkUsernameDuplicate(userDto.getUsername())){
                errorResponse.put("status","Fail");
                errorResponse.put("time",new Date());
                errorResponse.put("response", "이미 존재하는 유저입니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
            Long result= userService.signup(userDto);
            response.put("status","Success");
            response.put("time", new Date());
            response.put("response","회원가입 성공");
            return ResponseEntity.ok(response);
        }catch(Exception e){ // 회원가입 실패 시
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");

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

    @PostMapping("/signup/exists/email")
    public ResponseEntity<Map<String,Object>> checkEmailDuplicate(@RequestBody UserDTO userDto){
        Map<String,Object> response=new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        try{
            boolean result= userService.checkEmailDuplicate(userDto.getEmail());
            response.put("isEmailExists",result);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    //회원탈퇴
    @PostMapping("/user/delete")
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

        try { //탈퇴처리
            if(!userService.checkUserExist(uNum)){
                errorResponse.put("status","Fail");
                errorResponse.put("time",new Date());
                errorResponse.put("response", "존재하지 않는 회원입니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
            userService.deleteUser(uNum);
            response.put("status", "Success");
            response.put("time", new Date());
            response.put("response", "회원탈퇴 완료.");
            return ResponseEntity.ok(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    //회원정보 수정
    @PostMapping("/user/update")
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

        //수정 처리
        try {
            if(!userService.checkUserExist(userDto.getUNum())){
                errorResponse.put("status","Fail");
                errorResponse.put("time",new Date());
                errorResponse.put("response", "존재하지 않는 회원입니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
            userService.updateUser(userDto);
            response.put("status", "Success");
            response.put("time", new Date());
            response.put("response", "회원정보 수정 완료.");
            return ResponseEntity.ok(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
