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
    Boolean checkEmailDuplicate(String email);
    Boolean checkUsernameDuplicate(String username);

    @Controller
    @AllArgsConstructor
    class UserController extends HttpServlet {

        private final UserService userService;

        @PostMapping(value="/signup")
        public ResponseEntity<Map<String,Object>> signup(@RequestBody UserDTO userDto, HttpServletResponse res) throws IOException {

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
        public ResponseEntity<Map<String,Object>> checkUsernameDuplicate(@RequestParam("username") String username){
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
}
