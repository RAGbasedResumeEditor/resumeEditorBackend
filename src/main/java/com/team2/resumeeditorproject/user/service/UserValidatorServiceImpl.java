package com.team2.resumeeditorproject.user.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.team2.resumeeditorproject.common.ServiceSpecConstants;
import com.team2.resumeeditorproject.exception.DelDateException;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserValidatorServiceImpl implements UserValidatorService {
	private final UserRepository userRepository;

	@Override
	public void checkUsernameDuplicate(String username) {
		if (userRepository.existsByUsername(username)) {
			throw new IllegalStateException("username: " + username + " already exists");
		}
	}

	@Override
	public void checkEmailDuplicate(String email) {
		if (userRepository.existsByEmail(email)) {
			throw new IllegalStateException("email: " + email + " already exists");
		}
	}

	@Override
	public void checkCanRejoinAfterWithdrawal(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);

		if (optionalUser.isEmpty()) {
			return;
		}

		LocalDateTime deletedDate = optionalUser.get().getDeletedDate();

		if (deletedDate == null) {
			return;
		}

		LocalDateTime dateCanRejoin = deletedDate.plusDays(ServiceSpecConstants.DATE_CAN_REJOIN_AFTER_WITHDRAWAL);

		if (dateCanRejoin.isAfter(LocalDateTime.now())) {
			return;
		}

		List<String> result = new ArrayList<>();
		result.add(deletedDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
		result.add(dateCanRejoin.format(DateTimeFormatter.ISO_LOCAL_DATE));
		throw new DelDateException(result);
	}
}
