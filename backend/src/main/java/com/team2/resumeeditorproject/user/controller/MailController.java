package com.team2.resumeeditorproject.user.controller;

import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.service.MailService;
import com.team2.resumeeditorproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.team2.resumeeditorproject.admin.service.ResponseHandler.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/signup")
public class MailController {

    private final MailService mailService;
    private final UserService userService;


    @PostMapping("/auth-code") // 사용자에게 이메일을 보낸다.
    public ResponseEntity<Map<String, Object>> mailSend(@RequestBody UserDTO userDto) throws AuthenticationException {
        String email=userDto.getEmail();
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