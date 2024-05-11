package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // 비밀번호 암호화 처리

    //회원가입
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
                .email(userEmail)
                .username(userDto.getUsername())
                .password(bCryptPasswordEncoder.encode(userPassword))
                .role("ROLE_USER") // 바꿀 예정
                .birthDate(userDto.getBirthDate())
                .age(userDto.getAge())
                .gender(userDto.getGender())
                .occupation((userDto.getOccupation()))
                .company(userDto.getCompany())
                .wish(userDto.getWish())
                .status(userDto.getStatus())
                .mode(1)
                .build();
        return userRepository.save(user).getUNum();
    }
    /*@Override
    public Boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }*/
    @Override
    public Boolean checkUsernameDuplicate(String username) {
        return userRepository.existsByUsername(username);
    }

    //회원탈퇴
    @Override
    public void deleteUser(Long uNum){
        userRepository.deleteById(uNum);
    }

    //회원 비밀번호 수정
    @Override
    @Transactional
    public void updateUserPw(UserDTO userDto){
        User user=userRepository.findById(userDto.getUNum()).orElseThrow(()->{
            return new IllegalArgumentException("해당 유저가 존재하지 않습니다.");
        });
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }
    //회원정보 수정
    @Override
    @Transactional
    public void updateUser(UserDTO userDto) {
        User user=userRepository.findById(userDto.getUNum()).orElseThrow(()->{
            return new IllegalArgumentException("해당 유저가 존재하지 않습니다.");
        });
        //System.out.println("User: "+user.getUsername());
        //System.out.println("changed username: "+userDto.getUsername());
        //System.out.println("Mode: "+userDto.getMode());
        user.setGender(userDto.getGender());
        user.setBirthDate(userDto.getBirthDate());
        user.setStatus(userDto.getStatus());
        user.setCompany(userDto.getCompany());
        user.setOccupation(userDto.getOccupation());
        user.setWish(userDto.getWish());
        userRepository.save(user);
    }
}
