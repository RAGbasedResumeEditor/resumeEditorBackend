package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.admin.service.UserManagementService;
import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.resume.repository.CompanyRepository;
import com.team2.resumeeditorproject.resume.repository.OccupationRepository;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final OccupationRepository occupationRepository;
    private final CompanyRepository companyRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // 비밀번호 암호화 처리

    private final UserManagementService userManagementService;
    private final RefreshService refreshService;

    @Override
    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    @Override
    public Boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public int updateUserMode(long userNo) {
        return userRepository.updateUserMode(userNo);
    }

    @Override
    public User showUser(String username) {
        return userRepository.findByUsername(username);
    }

    //회원탈퇴 (del_date 필드에 날짜 추가)
    @Override
    public void deleteUser(Long userNo){
        User user = findUser(userNo);

        if (user.getDeletedDate() != null) {
            throw new BadRequestException("User already deleted with id: " + userNo);
        }

        // 회원 탈퇴 처리 후 DB에 탈퇴 날짜 업데이트
        userManagementService.updateUserDeleteDate(userNo);

        // 해당 사용자의 refresh 토큰 정보 삭제
        refreshService.deleteRefreshByUsername(user.getUsername());

        saveUser(user);
    }

    @Override
    public Boolean checkUserExist(Long userNo){
        Optional<User> user = userRepository.findById(userNo);
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
        User user = userRepository.findById(userDTO.getUserNo()).orElseThrow(()-> new IllegalArgumentException("Invalid user ID"));
        if (userDTO.getGender() != null) {
            user.setGender(userDTO.getGender());
        }

        if (userDTO.getAge() != null) {
            user.setAge(userDTO.getAge());
        }

        if (userDTO.getStatus() != null) {
            user.setStatus(userDTO.getStatus());
        }

        if (userDTO.getCompanyNo() != null) {
            user.setCompany(companyRepository.findById(userDTO.getCompanyNo()).orElseThrow(() -> new IllegalArgumentException("Invalid Company No: " + userDTO.getCompanyNo())));
        }

        if (userDTO.getOccupationNo() != null) {
            user.setOccupation(occupationRepository.findById(userDTO.getOccupationNo()).orElseThrow(() -> new IllegalArgumentException("Invalid Occupation No: " + userDTO.getOccupationNo())));
        }

        if (userDTO.getWishCompanyNo() != null) {
            user.setWishCompany(companyRepository.findById(userDTO.getWishCompanyNo()).orElseThrow(() -> new IllegalArgumentException("Invalid Company No: " + userDTO.getWishCompanyNo())));
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
    public User findUser(Long userNo) {
        return userRepository.findById(userNo)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userNo));
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
