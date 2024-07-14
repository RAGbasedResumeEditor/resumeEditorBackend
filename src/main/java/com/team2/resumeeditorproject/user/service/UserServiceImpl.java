package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.admin.service.UserManagementService;
import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // 비밀번호 암호화 처리

    private final UserManagementService userManagementService;
    private final RefreshService refreshService;

    @Override
    public Boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

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
        User user = findUser(uNum);

        if (user.getDelDate() != null) {
            throw new BadRequestException("User already deleted with id: " + uNum);
        }

        // 회원 탈퇴 처리 후 DB에 탈퇴 날짜 업데이트
        userManagementService.updateUserDeleteDate(uNum);

        // 해당 사용자의 refresh 토큰 정보 삭제
        refreshService.deleteRefreshByUsername(user.getUsername());

        saveUser(user);
    }

    @Override
    public Boolean checkUserExist(Long uNum){
        Optional<User> user = userRepository.findById(uNum);
        if (user.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    //회원정보 수정
    @Override
    @Transactional
    public void updateUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getUNum()).orElseThrow(()-> new IllegalArgumentException("Invalid user ID"));
        if (userDTO.getGender() != null) {
            user.setGender(userDTO.getGender());
        }

        if (userDTO.getAge() != null) {
            user.setAge(userDTO.getAge());
        }

        if (userDTO.getStatus() != null) {
            user.setStatus(userDTO.getStatus());
        }

        if (userDTO.getCompany() != null) {
            user.setCompany(userDTO.getCompany());
        }

        if (userDTO.getOccupation() != null) {
            user.setOccupation(userDTO.getOccupation());
        }

        if (userDTO.getWish() != null) {
            user.setWish(userDTO.getWish());
        }

        if (userDTO.getBirthDate() != null) {
            user.setBirthDate(userDTO.getBirthDate());
        }

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    public User findUser(Long uNum) {
        return userRepository.findById(uNum)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + uNum));
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
