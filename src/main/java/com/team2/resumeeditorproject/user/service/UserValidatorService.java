package com.team2.resumeeditorproject.user.service;

public interface UserValidatorService {
	void checkUsernameDuplicate(String username);

	void checkEmailDuplicate(String email);

	void checkCanRejoinAfterWithdrawal(String email);
}
