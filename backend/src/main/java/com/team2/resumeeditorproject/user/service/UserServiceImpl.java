package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Long signup(UserDTO userDto)   { //DB 저장

        String userEmail=userDto.getEmail();
        String userPassword=userDto.getPassword();

        //중복 검사
        Boolean isExists=userRepository.existsByEmail(userEmail);
        if(isExists){
            return -1L; //회원가입 실패
        }

        //회원가입 진행
        User user=User.builder() // User Entity
                .name(userDto.getName())
                .nickname(userDto.getNickname())
                .email(userEmail)
                .username(userDto.getUsername())
                .password(bCryptPasswordEncoder.encode(userPassword))
                .role("ROLE_USER") // 바꿀 예정
                .birthdate(userDto.getBirthdate())
                .age(userDto.getAge())
                .gender(userDto.getGender())
                .occupation((userDto.getOccupation()))
                .company(userDto.getCompany())
                .wish(userDto.getWish())
                .status(userDto.getStatus())
                .build();
        return userRepository.save(user).getuNum();
    }
    @Override
    public Boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }
    @Override
    public Boolean checkUsernameDuplicate(String username) {
        return userRepository.existsByUsername(username);
    }
}
