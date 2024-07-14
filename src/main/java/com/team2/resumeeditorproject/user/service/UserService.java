package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;

public interface UserService {
    String getUsername();
    Boolean checkEmailDuplicate(String email);
    int updateUserMode(long u_num);
    User showUser(String username);
    void deleteUser(Long uNum);
    void updateUser(UserDTO userDto);
    User findUser(Long uNum);
    void saveUser(User user);
}
