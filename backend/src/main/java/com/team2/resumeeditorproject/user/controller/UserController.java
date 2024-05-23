package com.team2.resumeeditorproject.user.controller;

import com.team2.resumeeditorproject.admin.service.UserManagementService;
import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.CustomUserDetails;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.RefreshRepository;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import com.team2.resumeeditorproject.user.service.UserService;
import jakarta.servlet.http.HttpServlet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.team2.resumeeditorproject.admin.service.ResponseHandler.*;

@Controller
@RequiredArgsConstructor
public class UserController extends HttpServlet {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RefreshRepository refreshRepository;
    private final UserManagementService userManagementService;


    public static String getUsername(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails= (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    //회원가입
    @PostMapping(value="/signup")
    public ResponseEntity<Map<String,Object>> signup(@RequestBody UserDTO userDto) throws IOException {
        String username=userDto.getUsername();
            if(userService.checkUsernameDuplicate(username)){
                throw new BadRequestException("["+username+"] 이미 존재하는 username 입니다.");
            }
            userService.signup(userDto);//회원가입 처리
            return createResponse("회원가입 성공");
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

    //회원조회
    @PostMapping("/user/search")
    public ResponseEntity<Map<String, Object>> showUser(){

        String username= getUsername();

            User tempUser=userService.showUser(username);
            UserDTO user=new UserDTO();
                user.setUNum(tempUser.getUNum());
                user.setEmail(tempUser.getEmail());
                user.setUsername((tempUser.getUsername()));
                user.setRole(tempUser.getRole());
                user.setAge(tempUser.getAge());
                user.setBirthDate(tempUser.getBirthDate());
                user.setGender(tempUser.getGender());
                user.setCompany(tempUser.getCompany());
                user.setOccupation(tempUser.getOccupation());
                user.setWish(tempUser.getWish());
                user.setStatus(tempUser.getStatus());
                user.setMode(tempUser.getMode());
                user.setInDate(tempUser.getInDate());
                user.setDelDate(tempUser.getDelDate());
            return createResponse(user);
    }

    //회원탈퇴
    @PostMapping("/user/delete")
    public ResponseEntity<Map<String, Object>> deleteUser() throws AuthenticationException{

        String username= getUsername();
        Long uNum=userService.showUser(username).getUNum();

            // 회원 탈퇴 처리 후 DB에 탈퇴 날짜 업데이트
            userManagementService.updateUserDeleteDate(uNum);

            // 해당 사용자의 refresh 토큰 정보 삭제
            User deletedUser = userRepository.findById(uNum)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + uNum));
            refreshRepository.deleteRefreshByUsername(deletedUser.getUsername());;

            return createResponse( uNum+"번 회원 탈퇴 완료.");
    }

    //회원정보 수정
    @PostMapping("/user/update")
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UserDTO userDto) throws AuthenticationException{
        String username= getUsername();
        User tempUser=userService.showUser(username);
        userDto.setUNum(tempUser.getUNum());

        userService.updateUser(userDto);//수정 처리
        return createResponse(getUsername()+" 회원 수정 완료.");
    }
}
