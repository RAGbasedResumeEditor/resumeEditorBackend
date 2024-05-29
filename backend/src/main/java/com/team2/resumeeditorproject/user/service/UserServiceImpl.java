package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // 비밀번호 암호화 처리

    //회원가입
    @Override
    public void signup(UserDTO userDto)   {
        //회원가입 진행
        User user=User.builder()
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .role("ROLE_USER")
                .birthDate(userDto.getBirthDate())
                .age(userDto.getAge())
                .gender(userDto.getGender())
                .occupation((userDto.getOccupation()))
                .company(userDto.getCompany())
                .wish(userDto.getWish())
                .status(userDto.getStatus())
                .mode(1)
                .build();
        userRepository.save(user);
    }
    @Override
    public Boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }
    @Override
    public Boolean checkUsernameDuplicate(String username) {
        return userRepository.existsByUsername(username);
    }
    /* eunbi */
    @Override
    @Transactional
    public int updateUserMode(long u_num) {
        return userRepository.updateUserMode(u_num);
    }

    @Override
    public User showUser(String username) {
        return userRepository.findByUsername(username);
    }

    //회원탈퇴 (del_date 필드에 날짜 추가)
    @Override
    public void deleteUser(Long uNum){
        userRepository.deleteById(uNum);
    }

    @Override
    public Boolean checkUserExist(Long uNum){
        Optional<User> user=userRepository.findById(uNum);
        if(user.isPresent()){
            return true;
        }else{
            return false;
        }
    }
    //회원정보 수정
    @Override
    @Transactional
    public void updateUser(UserDTO userDto) {
        User user=userRepository.findById(userDto.getUNum()).orElseThrow(()-> new IllegalArgumentException("Invalid user ID"));
        if (userDto.getGender()!=null) {
            user.setGender(userDto.getGender());
        }

        if (userDto.getAge() != null) {
            user.setAge(userDto.getAge());
        }

        if (userDto.getStatus() != null) {
            user.setStatus(userDto.getStatus());
        }

        if (userDto.getCompany() != null) {
            user.setCompany(userDto.getCompany());
        }

        if (userDto.getOccupation() != null) {
            user.setOccupation(userDto.getOccupation());
        }

        if (userDto.getWish() != null) {
            user.setWish(userDto.getWish());
        }

        if (userDto.getBirthDate() != null) {
            user.setBirthDate(userDto.getBirthDate());
        }

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        }
        userRepository.save(user);
    }
}
