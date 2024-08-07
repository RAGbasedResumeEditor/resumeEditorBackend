package com.team2.resumeeditorproject.user.service;

import java.util.Optional;

import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;

public interface UserService {
	int updateUserMode(long userNo);

	@Deprecated
	User showUser(String username);

	UserDTO getUserByUsername(String username);

	void deleteUser(Long userNo);

	void updateUser(UserDTO userDto);

	User findUser(Long userNo);

	void saveUser(User user);
}
