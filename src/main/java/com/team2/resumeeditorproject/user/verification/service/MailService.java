package com.team2.resumeeditorproject.user.verification.service;

public interface MailService {
	boolean checkAuthNum(String email, String authCode);

	void sendEmail(String email);
}
