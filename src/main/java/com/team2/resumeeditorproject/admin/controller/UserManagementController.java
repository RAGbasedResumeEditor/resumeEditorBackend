package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.dto.request.SearchUserRequest;
import com.team2.resumeeditorproject.admin.dto.response.DeleteUserResultResponse;
import com.team2.resumeeditorproject.admin.dto.response.UserListResponse;
import com.team2.resumeeditorproject.admin.service.UserDeleteService;
import com.team2.resumeeditorproject.admin.service.UserManagementService;
import com.team2.resumeeditorproject.exception.NotFoundException;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserManagementController {

    private final UserManagementService userManagementService;
    private final UserDeleteService userDeleteService;

    private static final int SIZE_OF_PAGE = 20; // 한 페이지에 보여줄 회원 수

    /* 회원 목록 */
    @GetMapping("/list")
    public ResponseEntity<UserListResponse> getUserList(
            @RequestParam(defaultValue = "0", name = "pageNo") int pageNo) {

        int size = SIZE_OF_PAGE;

        // 음수값이라면 0 페이지로 이동
        if (pageNo < 0) {
            pageNo = 0;
        }

        Pageable pageable = PageRequest.of(pageNo, size);
        Page<UserDTO> userPage = userManagementService.getUserList(pageable);

        if (userPage.getTotalElements() == 0) { // 유저 목록이 없는 경우
            throw new NotFoundException("유저가 존재하지 않습니다.");
        }

        // page가 totalPages보다 크면 마지막 페이지로 이동
        if (pageNo >= userPage.getTotalPages()) {
            // 마지막 페이지로 이동
            pageNo = Math.max(userPage.getTotalPages() - 1, 0);  // totalPages - 1 또는 0
            pageable = PageRequest.of(pageNo, size);
            userPage = userManagementService.getUserList(pageable);
        }

        return ResponseEntity.ok()
                .body(UserListResponse.builder()
                        .totalPage(userPage.getTotalPages())
                        .userPage(userPage.getContent())
                        .build());
    }

    /* 회원 검색 */
    @GetMapping("/search")
    public ResponseEntity<UserListResponse> searchUsers(@RequestBody SearchUserRequest searchUserRequest) {

        int size = SIZE_OF_PAGE;
        int pageNo = searchUserRequest.getPageNo();
        String group = searchUserRequest.getGroup();
        String keyword = searchUserRequest.getKeyword();

        // 음수값이라면 0 페이지로 이동
        if (pageNo < 0) {
            searchUserRequest.setPageNo(0);
        }

        Pageable pageable = PageRequest.of(pageNo, size);
        Page<UserDTO> userPage = userManagementService.searchUsersByGroupAndKeyword(group, keyword, pageable);

        if (userPage.getTotalElements() == 0) { // 검색 결과 목록이 없는 경우
            throw new NotFoundException("검색 결과가 존재하지 않습니다.");
        }

        // page가 totalPages보다 크면 마지막 페이지로 이동
        if (pageNo >= userPage.getTotalPages()) {
            pageNo = Math.max(userPage.getTotalPages() - 1, 0);  // totalPages - 1 또는 0
            pageable = PageRequest.of(pageNo, size);
            // 페이지를 새로 요청하여 `userPage`를 업데이트
            userPage = userManagementService.searchUsersByGroupAndKeyword(group, keyword, pageable);
        }

        return ResponseEntity.ok()
                .body(UserListResponse.builder()
                        .totalPage(userPage.getTotalPages())
                        .userPage(userPage.getContent())
                        .build());
    }

    /* 회원 탈퇴 */
    @DeleteMapping("/{uNum}")
    public ResponseEntity<DeleteUserResultResponse> deleteUser(@PathVariable("uNum") long uNum) {
        userDeleteService.deleteUser(uNum);

        return ResponseEntity.ok()
                .body(DeleteUserResultResponse.builder()
                        .response("User deleted successfully")
                        .status("Success")
                        .time(new Date())
                        .build());
    }
}
