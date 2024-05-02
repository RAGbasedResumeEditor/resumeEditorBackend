package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.user.dto.UserDTO;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;

public interface UserService {
    Long signup(UserDTO userDto); // 회원가입
    Boolean checkEmailDuplicate(String email);
    Boolean checkNicknameDuplicate(String nickname);
}
