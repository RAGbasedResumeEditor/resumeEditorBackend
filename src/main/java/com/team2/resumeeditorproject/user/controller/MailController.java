package com.team2.resumeeditorproject.user.controller;

import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.exception.DelDateException;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import com.team2.resumeeditorproject.user.service.MailService;
import com.team2.resumeeditorproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.team2.resumeeditorproject.admin.service.ResponseHandler.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/signup")
public class MailController {

    private final MailService mailService;
    private final UserService userService;
    private final UserRepository userRepository;


    @PostMapping("/auth-code") // 사용자에게 이메일을 보낸다.
    public ResponseEntity<Map<String, Object>> mailSend(@RequestBody UserDTO userDto) throws AuthenticationException {
        String email=userDto.getEmail();
        if(userRepository.findByEmail(email)!=null){
            Date delDate=userRepository.findByEmail(email).getDelDate();
            // del_date가 30일 이내인 경우
            if(delDate!=null) {
                // Calendar 인스턴스를 생성하여 delDate로 설정합니다.
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String deleted = dateFormat.format(delDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(delDate);
                // 30일을 추가합니다.
                calendar.add(Calendar.DAY_OF_MONTH, 30);
                // 새로운 날짜를 가져옵니다.
                Date newDate = calendar.getTime();
                String available = dateFormat.format(newDate);
                if(delDate.before(newDate)){
                    List<String> result=new ArrayList<>();
                    result.add(deleted);
                    result.add(available);
                    throw new DelDateException(result);
                }
            }

        }
        // 이미 가입된 email인 경우
        if(userService.checkEmailDuplicate(userDto.getEmail())){
            throw new BadRequestException(email+" already exists");
        }

        mailService.sendEmail(email);
        return createResponse("인증 코드 전송 성공");
    }

    @PostMapping("/auth-check")
    public ResponseEntity<Map<String, Object>> authCheck(@RequestBody UserDTO userDto) throws AuthenticationException{

        String email=userDto.getEmail();
        String authCode=userDto.getAuthCode();

        boolean checked=mailService.checkAuthNum(email, authCode);

        if (checked) {
            return createResponse("인증 성공");
        } else {
            return createBadReqResponse("인증 실패.");
        }
    }
}