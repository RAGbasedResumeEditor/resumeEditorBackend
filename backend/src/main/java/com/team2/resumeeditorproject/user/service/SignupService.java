package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignupService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SignupService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void signupProcess(UserDTO userDTO) {

        String email = userDTO.getEmail();
        String password = userDTO.getPassword();
        // 테스트 위해 not null 모두 값 넣어줌
        String username = userDTO.getUsername();
        int birthdate = userDTO.getBirthdate();
        int age = userDTO.getAge();
        char gender = userDTO.getGender();
        String nickName = userDTO.getNickname();
        int status = userDTO.getStatus();
        int mode = userDTO.getMode();
        String name = userDTO.getName();


        Boolean isExist = userRepository.existsByEmail(email);

        //이메일이 존재한다면 메서드 종료시킴
        if (isExist) {

            return;
        }

        User data = new User();

        data.setEmail(email);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");
        // 테스트 위해 not null 모두 값 넣어줌
        data.setUsername(username);
        data.setBirthdate(birthdate);
        data.setAge(age);
        data.setGender(gender);
        data.setNickname(nickName);
        data.setStatus(status);
        data.setMode(mode);
        data.setName(name);

        userRepository.save(data);
    }
}
