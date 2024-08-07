package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.refresh.service.RefreshService;
import com.team2.resumeeditorproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDeleteServiceImpl implements UserDeleteService{

    private final RefreshService refreshService;
    private final UserService userService;
    private final UserManagementService userManagementService;

    @Override
    public void deleteUser(Long userNo) {
        // userNo으로 사용자 조회
        User user = userService.findUser(userNo);

        // 사용자가 이미 삭제된 상태인지 확인
        if (user.getDeletedDate() != null) {
            throw new BadRequestException("User already deleted with id: " + userNo);
        }

        // 회원 탈퇴 처리 후 DB에 탈퇴 날짜 업데이트
        userManagementService.updateUserDeleteDate(userNo);

        // 해당 사용자의 refresh 토큰 정보 삭제
        refreshService.deleteRefreshByUsername(user.getUsername());

        // 회원의 role을 ROLE_BLACKLIST 로 변경
        user.setRole("ROLE_BLACKLIST");
        userService.saveUser(user);
    }
}
