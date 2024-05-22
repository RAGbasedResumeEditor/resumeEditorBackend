package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.user.Error.UserBlacklistedException;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.CustomUserDetails;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
// DB 연결하여 회원 조회하기 위한 클래스
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        CustomUserDetails userDetails = new CustomUserDetails(user);

        String role = user.getRole();
        if (role.equals("ROLE_BLACKLIST") && !userDetails.isEnabled()) {
            throw new UserBlacklistedException("User is blacklisted and cannot log in until " + userDetails.getReactivationDate());
        }

        return userDetails;
    }
}
