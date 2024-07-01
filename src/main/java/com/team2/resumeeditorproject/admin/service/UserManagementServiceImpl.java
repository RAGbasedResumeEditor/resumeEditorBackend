package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminUserRepository;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService{

    private final AdminUserRepository adminUserRepository;

    private Page<UserDTO> convertToUserDTOPage(Page<Object[]> resultPage) {
        List<UserDTO> userDTOList = resultPage.getContent().stream().map(record -> {
            User user = (User) record[0];
            Long resumeEditCount = (Long) record[1];

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(user.getUsername());
            userDTO.setEmail(user.getEmail());
            userDTO.setGender(user.getGender());
            userDTO.setBirthDate(user.getBirthDate());
            userDTO.setCompany(user.getCompany());
            userDTO.setOccupation(user.getOccupation());
            userDTO.setWish(user.getWish());
            userDTO.setStatus(user.getStatus());
            userDTO.setMode(user.getMode());
            userDTO.setInDate(user.getInDate());
            userDTO.setDelDate(user.getDelDate());
            userDTO.setUNum(user.getUNum());
            userDTO.setRole(user.getRole());
            userDTO.setAge(user.getAge());
            userDTO.setResumeEditCount(resumeEditCount.intValue());

            return userDTO;
        }).collect(Collectors.toList());

        return new PageImpl<>(userDTOList, resultPage.getPageable(), resultPage.getTotalElements());
    }

    // 회원 목록 + 페이징
    @Override
    public Page<UserDTO> getUserList(Pageable pageable) {
        Page<Object[]> userPage = adminUserRepository.findUsersWithResumeEditCount(pageable);
        return convertToUserDTOPage(userPage);
    }

    // 그룹, 키워드 검색 + 페이징
    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> searchUsersByGroupAndKeyword(String group, String keyword, Pageable pageable) {
        Page<Object[]> userPage = adminUserRepository.findByGroupAndKeyword(group, keyword, pageable);
        return convertToUserDTOPage(userPage);
    }

    // 회원탈퇴 (del_date 필드에 날짜 추가)
    @Override
    public void updateUserDeleteDate(Long uNum){
        User user = adminUserRepository.findById(uNum)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + uNum));
        user.setDelDate(new Date()); // 현재 시간으로 탈퇴 날짜 업데이트
        adminUserRepository.save(user);
    }

    @Override
    public void updateDelDateForRoleBlacklist() {
        // 현재 날짜 가져오기
        Date currentDate = new Date();
        // 60일 전 날짜 계산
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, -60);
        Date dateBefore60Days = calendar.getTime();

        // "ROLE_BLACKLIST"인 사용자의 정보를 가져옴
        List<User> blacklistedUsers = adminUserRepository.findByRole("ROLE_BLACKLIST");

        // 가져온 사용자 중 del_date가 60일 이상 지난 사용자의 del_date를 null로 업데이트
        for (User user : blacklistedUsers) {
            if (user.getDelDate() != null && user.getDelDate().before(dateBefore60Days)) {
                user.setDelDate(null);
            }
        }

        adminUserRepository.saveAll(blacklistedUsers);
    }
}
