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
                .mode(userDto.getMode())
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

    //회원탈퇴 => 30일 동안 정보 보관하고 삭제 처리 (코드 수정해야함)
    @Override
    public void withdraw(Long uNum){
        userRepository.deleteById(uNum);
    }

    //회원정보 수정
    @Override
    @Transactional
    public void userUpdate(UserDTO userDto) {

        //업데이트 처리
        //유저네임, 비밀번호, 성별, 생년월일, status, company, occupation, wish, mode
        //유저네임은 유효성 검사
        //수정 처리
        User user=userRepository.findById(userDto.getUNum()).orElseThrow(()->{
            //예외 처리
            return new IllegalArgumentException("해당 유저가 존재하지 않습니다.");
        });
        user.setUsername(userDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);
        //회원 수정 함수 종료 시 = 서비스 종료 = 트랜젝션 종료 = 자동으로 commit
    }
}
