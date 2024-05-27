package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminUserRepository;
import com.team2.resumeeditorproject.admin.repository.AdminResumeEditRepository;
import com.team2.resumeeditorproject.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService{

    private final AdminUserRepository adminUserRepository;
    private final AdminResumeEditRepository adminResumeEditRepository;

    // 회원 목록 + 페이징
    @Override
    @Transactional(readOnly = true)
    public Page<User> getAllUsersPaged(Pageable pageable) {
        return adminUserRepository.findAll(pageable);
    }

    // 첨삭 횟수
    @Override
    public int getResumeEditCountByRNum(Long uNum) {
        return adminResumeEditRepository.countByRNum(uNum);
    }

    // 그룹, 키워드 검색 + 페이징
    @Override
    @Transactional(readOnly = true)
    public Page<User> searchUsersByGroupAndKeyword(String group, String keyword, Pageable pageable) {
        return switch (group) {
            case "username" -> adminUserRepository.findByUsernameContainingOrderByInDateDesc(keyword, pageable);
            case "email" -> adminUserRepository.findByEmailContainingOrderByInDateDesc(keyword, pageable);
            case "company" -> adminUserRepository.findByCompanyContainingOrderByInDateDesc(keyword, pageable);
            case "occupation" -> adminUserRepository.findByOccupationContainingOrderByInDateDesc(keyword,pageable);
            case "wish" -> adminUserRepository.findByWishContainingOrderByInDateDesc(keyword,pageable);
            default -> adminUserRepository.findAll(pageable);
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

    // 매일 자정에 실행되는 스케줄러 설정
    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    public void updateDelDateForRoleBlacklist() {
        // 현재 날짜 가져오기
        Date currentDate = new Date();
        // 60일 전 날짜 계산
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, -60);
        Date dateBefore60Days = calendar.getTime();

        // UserRepository를 통해 role이 "ROLE_BLACKLIST"이고 del_date가 60일 이전인 사용자의 정보를 가져옴
        List<User> usersToConvert = adminUserRepository.findByRoleAndDelDateBefore("ROLE_BLACKLIST", dateBefore60Days);

        // 가져온 사용자들의 role을 "ROLE_USER"로 변경하고, del_date를 null로 업데이트
        for (User user : usersToConvert) {
            user.setRole("ROLE_USER");
            user.setDelDate(null);
        }

        // 변경된 정보를 데이터베이스에 저장
        adminUserRepository.saveAll(usersToConvert);
    }
    /*
    // 30일 지나면 테이블에서 해당 회원 삭제
    @Override
    @Transactional
    @Scheduled(cron = "0 0 12 * * *") // 매일 오후 12시에 메서드 동작
    public void deleteUserEnd(){
        adminUserRepository.deleteByDelDateLessThanEqual((LocalDateTime.now().minusDays(30)));
    }
     */
}
