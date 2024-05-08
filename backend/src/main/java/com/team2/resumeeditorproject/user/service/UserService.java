package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.user.dto.UserDTO;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;

public interface UserService {
    Long signup(UserDTO userDto); // 회원가입
    Boolean checkEmailDuplicate(String email);
<<<<<<< HEAD
    Boolean checkUsernameDuplicate(String nickname);
=======
    Boolean checkUsernameDuplicate(String username);
>>>>>>> 8b97b5ac7d556187da118a8fe88d7f4326108f33
}
