package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminUserRepository;
import com.team2.resumeeditorproject.admin.repository.AdminResumeEditRepository;
import com.team2.resumeeditorproject.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService{

    private final AdminUserRepository adminUserRepository;
    private final AdminResumeEditRepository adminResumeEditRepository;

    // 회원 목록 + 페이징
    @Override
    public Page<User> getAllUsersByRolePaged(String role, Pageable pageable) {
        return adminUserRepository.findByRole(role, pageable);
    }

    // 첨삭 횟수
    @Override
    public int getResumeEditCountByRNum(Long uNum) {
        return adminResumeEditRepository.countByRNum(uNum);
    }

    // 그룹, 키워드 검색 + 페이징
    @Override
    public Page<User> searchUsersByGroupAndKeyword(String group, String keyword, String role, Pageable pageable) {
        return switch (group) {
            case "username" -> adminUserRepository.findByUsernameContainingAndRoleOrderByInDateDesc(keyword, role, pageable);
            case "email" -> adminUserRepository.findByEmailContainingAndRoleOrderByInDateDesc(keyword, role, pageable);
            case "company" -> adminUserRepository.findByCompanyContainingAndRoleOrderByInDateDesc(keyword, role, pageable);
            case "occupation" -> adminUserRepository.findByOccupationContainingAndRoleOrderByInDateDesc(keyword, role, pageable);
            case "wish" -> adminUserRepository.findByWishContainingAndRoleOrderByInDateDesc(keyword, role, pageable);
            default -> adminUserRepository.findByRole(role, pageable);
        };
    }

    // 회원탈퇴 (del_date 필드에 날짜 추가)
    @Override
    public void updateUserDeleteDate(Long uNum){
        User user = adminUserRepository.findById(uNum)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + uNum));
        user.setDelDate(new Date()); // 현재 시간으로 탈퇴 날짜 업데이트
        adminUserRepository.save(user);
    }

    // 30일 지나면 테이블에서 해당 회원 삭제
    @Override
    @Transactional
    @Scheduled(cron = "0 0 12 * * *") // 매일 오후 12시에 메서드 동작
    public void deleteUserEnd(){
        adminUserRepository.deleteByDelDateLessThanEqual((LocalDateTime.now().minusDays(30)));
    }
}
