package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;

public interface UserService {
    String getUsername();
    Boolean checkEmailDuplicate(String email);

    int updateUserMode(long userNo);
    User showUser(String username);
    void deleteUser(Long userNo);
    void updateUser(UserDTO userDto);
    User findUser(Long userNo);
    void saveUser(User user);
}
