package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.user.dto.UserDTO;


public interface UserService {
    Long signup(UserDTO userDto); // 회원가입
    Boolean checkEmailDuplicate(String email);
    Boolean checkUsernameDuplicate(String username);
}
