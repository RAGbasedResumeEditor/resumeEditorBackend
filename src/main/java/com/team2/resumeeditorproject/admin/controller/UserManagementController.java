package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.service.UserManagementService;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.RefreshRepository;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserManagementController {

    private final UserManagementService userManagementService;
    private final RefreshRepository refreshRepository;
    private final UserRepository userRepository;

    private static final int SIZE_OF_PAGE = 20; // 한 페이지에 보여줄 회원 수

    /* 회원 목록 */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getUserList(
            @RequestParam(defaultValue = "0", name = "pageNo") int pageNo) {

        try {
            int size = SIZE_OF_PAGE;

            // 음수값이라면 0 페이지로 이동
            if (pageNo < 0) {
                pageNo = 0;
            }

            Pageable pageable = PageRequest.of(pageNo, size);
            Page<UserDTO> userPage = userManagementService.getUserList(pageable);

            // page가 totalPages보다 크면 마지막 페이지로 이동
            if (pageNo >= userPage.getTotalPages()) {
                // 마지막 페이지로 이동
                pageNo = Math.max(userPage.getTotalPages() - 1, 0);  // totalPages - 1 또는 0
                pageable = PageRequest.of(pageNo, size);
                userPage = userManagementService.getUserList(pageable);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("response", userPage.getContent());
            response.put("totalPages", userPage.getTotalPages());

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("response", "server error " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /* 회원 검색 */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchUsers(
            @RequestParam(required = false, name = "group") String group,
            @RequestParam(required = false, name = "keyword") String keyword,
            @RequestParam(defaultValue = "0", name = "pageNo") int pageNo) {

        try {
            int size = SIZE_OF_PAGE;

            // 음수값이라면 0 페이지로 이동
            if (pageNo < 0) {
                pageNo = 0;
            }

            Pageable pageable = PageRequest.of(pageNo, size);
            Page<UserDTO> userPage;

            // group과 keyword가 있는 경우 검색, 그렇지 않으면 모든 사용자 목록
            if (group != null && keyword != null) {
                userPage = userManagementService.searchUsersByGroupAndKeyword(group, keyword, pageable);
            } else {
                userPage = userManagementService.getUserList(pageable);
            }

            // page가 totalPages보다 크면 마지막 페이지로 이동
            if (pageNo >= userPage.getTotalPages()) {
                pageNo = Math.max(userPage.getTotalPages() - 1, 0);  // totalPages - 1 또는 0
                pageable = PageRequest.of(pageNo, size);
                // 페이지를 새로 요청하여 `userPage`를 업데이트
                if (group != null && keyword != null) {
                    userPage = userManagementService.searchUsersByGroupAndKeyword(group, keyword, pageable);
                } else {
                    userPage = userManagementService.getUserList(pageable);
                }
            }

            Map<String, Object> response = new HashMap<>();
            response.put("response", userPage.getContent());
            response.put("totalPages", userPage.getTotalPages());

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("response", "server error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /* 회원 탈퇴 */
    @DeleteMapping("/{uNum}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable("uNum") long uNum) {
        Map<String, Object> response = new HashMap<>();
        try {
            // uNum으로 사용자 조회
            User user = userRepository.findById(uNum)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + uNum));

            // 사용자가 이미 삭제된 상태인지 확인
            if (user.getDelDate() != null) {
                throw new RuntimeException("User already deleted with id: " + uNum);
            }

            // 회원 탈퇴 처리 후 DB에 탈퇴 날짜 업데이트
            userManagementService.updateUserDeleteDate(uNum);

            // 해당 사용자의 refresh 토큰 정보 삭제
            refreshRepository.deleteRefreshByUsername(user.getUsername());

            // 회원의 role을 ROLE_BLACKLIST 로 변경
            user.setRole("ROLE_BLACKLIST");
            userRepository.save(user);

            response.put("response", "User deleted successfully");
            response.put("time", new Date());
            response.put("status", "Success");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("response", e.getMessage());
            response.put("time", new Date());
            response.put("status", "Fail");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("response", "Failed to delete user");
            response.put("time", new Date());
            response.put("status", "Fail");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
