package com.team2.resumeeditorproject.user.controller;

import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.service.UserService;
import jakarta.servlet.http.HttpServlet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.team2.resumeeditorproject.admin.service.ResponseHandler.*;

@Controller
@RequiredArgsConstructor
public class UserController extends HttpServlet {

    private final UserService userService;

    //회원가입
    @PostMapping(value="/signup")
    public ResponseEntity<Map<String,Object>> signup(@RequestBody UserDTO userDto) throws IOException {
        try{
            if(userService.checkUsernameDuplicate(userDto.getUsername())){
                return createBadReqResponse("이미 존재하는 username 입니다.");
            }
            if(userService.checkEmailDuplicate(userDto.getEmail())){
                return createBadReqResponse("이미 존재하는 email 입니다.");
            }
            userService.signup(userDto);//회원가입 처리
            return createResponse("회원가입 성공");
        }catch(Exception e){
            return createServerErrResponse();
        }
    }//signup()
/*
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
    }*/

    //회원탈퇴
    @PostMapping("/user/delete")
    public ResponseEntity<Map<String, Object>> deleteUser(@RequestBody UserDTO userDto) throws AuthenticationException{
        Map<String,Object> response=new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        Long unum=userDto.getUNum();
        if(unum==null){
            return createBadReqResponse("삭제할 unum을 입력해주세요.");
        }
        try {
            if(!userService.checkUserExist(unum)){
                return createBadReqResponse(unum+"번 유저는 존재하지 않는 회원입니다.");
            }
            userService.deleteUser(unum);//탈퇴처리
            return createResponse( unum+"번 회원 탈퇴 완료.");
        }catch(Exception e){
            return createServerErrResponse();
        }
    }

    //회원정보 수정
    @PostMapping("/user/update")
    public ResponseEntity<Map<String, Object>> edit(@RequestBody UserDTO userDto) throws AuthenticationException{
        try {
            if(!userService.checkUserExist(userDto.getUNum())){
                return createBadReqResponse(userDto.getUNum()+"번 유저는 존재하지 않는 회원입니다.");
            }
            if(userDto.getUNum()==null||userDto.getPassword()==null||userDto.getBirthDate()==null){
                return createBadReqResponse("비밀번호와 생년월일은 반드시 입력해야합니다.");
            }
            userService.updateUser(userDto);//수정 처리
            return createResponse(userDto.getUNum()+"번 회원 수정 완료.");
        }catch(Exception e){
            return createServerErrResponse();
        }
    }
}
