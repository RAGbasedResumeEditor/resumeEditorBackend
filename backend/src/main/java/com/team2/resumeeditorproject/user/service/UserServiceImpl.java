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
    public Long signup(UserDTO userDto)   {
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
        return userRepository.save(user).getUNum();
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
    public Optional<User> showUser(Long u_num) {
        return userRepository.findById(u_num);
    }

    //회원탈퇴 (del_date 필드에 날짜 추가)
    @Override
    public void deleteUser(Long uNum){
        userRepository.deleteById(uNum);
    }
    //30일 지나면 테이블에서 해당 회원 삭제
    @Override
    @Transactional
    @Scheduled(cron = "0 0 12 * * *") // 매일 오후 12시에 메서드 동작
    public void deleteUserEnd(){
        userRepository.deleteByDelDateLessThanEqual((LocalDateTime.now().minusDays(30)));
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
        User user=userRepository.findById(userDto.getUNum()).orElseThrow(()->{return null;});
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setGender(userDto.getGender());
        user.setBirthDate(userDto.getBirthDate());
        user.setAge(userDto.getAge());
        user.setStatus(userDto.getStatus());
        user.setCompany(userDto.getCompany());
        user.setOccupation(userDto.getOccupation());
        user.setWish(userDto.getWish());
        userRepository.save(user);
    }
}
