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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.team2.resumeeditorproject.common.util.ResponseHandler.createBadRequestResponse;
import static com.team2.resumeeditorproject.common.util.ResponseHandler.createOkResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/signup")
public class MailController {

    private final MailService mailService;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/auth-code") // 사용자에게 이메일을 보낸다.
    public ResponseEntity<Map<String, Object>> mailSend(@RequestBody UserDTO userDTO) throws AuthenticationException {
        String email = userDTO.getEmail();
        if (userRepository.findByEmail(email) != null){
            Date delDate = userRepository.findByEmail(email).getDeletedDate();
            // del_date가 30일 이내인 경우
            if (delDate!=null) {
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
                if (delDate.before(newDate)) {
                    List<String> result=new ArrayList<>();
                    result.add(deleted);
                    result.add(available);
                    throw new DelDateException(result);
                }
            }

        }
        // 이미 가입된 email인 경우
        if (userService.checkEmailDuplicate(userDTO.getEmail())) {
            throw new BadRequestException(email + " already exists");
        }

        mailService.sendEmail(email);
        return createOkResponse("인증 코드 전송 성공");
    }

    @PostMapping("/auth-check")
    public ResponseEntity<Map<String, Object>> authCheck(@RequestBody UserDTO userDTO) throws AuthenticationException {

        String email = userDTO.getEmail();
        String authCode = userDTO.getAuthCode();

        boolean checked = mailService.checkAuthNum(email, authCode);

        if (checked) {
            return createOkResponse("인증 성공");
        } else {
            return createBadRequestResponse("인증 실패.");
        }
    }
}
