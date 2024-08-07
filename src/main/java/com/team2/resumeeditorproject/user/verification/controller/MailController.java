package com.team2.resumeeditorproject.user.verification.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team2.resumeeditorproject.common.dto.response.CommonResponse;
import com.team2.resumeeditorproject.exception.ErrorResponse;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.service.UserValidatorService;
import com.team2.resumeeditorproject.user.verification.service.MailService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/signup")
public class MailController {

	private final MailService mailService;
	private final UserValidatorService userValidatorService;

	@PostMapping("/auth-code")
	public ResponseEntity<?> SendMail(@RequestBody UserDTO userDTO) {
		String email = userDTO.getEmail();

		userValidatorService.checkCanRejoinAfterWithdrawal(email);
		userValidatorService.checkEmailDuplicate(email);

		mailService.sendEmail(email);

		return ResponseEntity.ok()
				.body(CommonResponse.builder()
						.response("인증 코드 전송 성공")
						.status("Success")
						.time(new Date())
						.build());
	}

	@PostMapping("/auth-check")
	public ResponseEntity<?> CheckAuthentication(@RequestBody UserDTO userDTO) throws AuthenticationException {
		String email = userDTO.getEmail();
		String authCode = userDTO.getAuthCode();
		boolean checked = mailService.checkAuthNum(email, authCode);
		if (checked) {
			return ResponseEntity.ok()
					.body(CommonResponse.builder()
							.response("인증 성공")
							.status("Success")
							.time(new Date())
							.build());
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ErrorResponse.builder()
							.response("인증 실패")
							.time(new Date())
							.build());
		}
	}
}
