package com.team2.resumeeditorproject.user.controller;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.team2.resumeeditorproject.common.dto.response.CommonResponse;
import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.service.SignupService;

import lombok.RequiredArgsConstructor;

@Deprecated
@Controller
@RequiredArgsConstructor
public class SignupController {

	private final SignupService signupService;

	// 회원가입
	@Deprecated // replaced UserController.signup
	@PostMapping("/signup")
	public ResponseEntity<CommonResponse<String>> signup(@RequestBody UserDTO userDTO) {
		String username = userDTO.getUsername();

		signupService.checkCanRejoinAfterWithdrawal(userDTO.getUsername());
		signupService.checkUsernameDuplicate(username);

		signupService.signup(userDTO);

		return ResponseEntity.ok()
				.body(CommonResponse.<String>builder()
						.response("회원가입 성공")
						.status("Success")
						.time(new Date())
						.build());
	}
}
