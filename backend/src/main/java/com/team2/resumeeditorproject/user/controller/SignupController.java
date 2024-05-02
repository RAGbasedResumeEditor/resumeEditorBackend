package com.team2.resumeeditorproject.user.controller;

import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.service.SignupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class SignupController {
    private final SignupService signupService;

    public SignupController(SignupService signupService){
        this.signupService = signupService;
    }

    @PostMapping("signup")
    public String signupProcess(UserDTO userDTO){
        //System.out.println("회원가입 처리 시 email : "+userDTO.getEmail());
        signupService.signupProcess(userDTO);

        return "ok";
    }
}
