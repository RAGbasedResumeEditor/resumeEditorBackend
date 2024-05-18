package com.team2.resumeeditorproject.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.resumeeditorproject.admin.repository.AdminResumeEditRepository;
import com.team2.resumeeditorproject.admin.repository.AdminUserRepository;
import com.team2.resumeeditorproject.admin.service.UserManagementService;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.RefreshRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/admin/user")
public class UserManagementController {

    private final UserManagementService userManagementService;
    private final AdminResumeEditRepository adminResumeEditRepository;
    private final RefreshRepository refreshRepository;

    public UserManagementController(UserManagementService userManagementService, AdminResumeEditRepository adminResumeEditRepository,
                                    RefreshRepository refreshRepository){
        this.userManagementService = userManagementService;
        this.adminResumeEditRepository = adminResumeEditRepository;
        this.refreshRepository = refreshRepository;
    }

    private ResponseEntity<Map<String, Object>> createResponse(Page<User> userList){
        List<UserDTO> userDTOList = new ArrayList<>();

        for(User user : userList){
            long uNum = user.getUNum();
            int resumeEditCount = adminResumeEditRepository.countByRNum(uNum);

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

            // 유저 첨삭횟수
            userDTO.setResumeEditCount(resumeEditCount);

            userDTOList.add(userDTO);
        }

        // 전체 페이지 수
        int totalPages = userList.getTotalPages();

        Map<String, Object> response = new HashMap<>();
        response.put("response", userDTOList);
        response.put("totalPages",totalPages);

        return ResponseEntity.ok().body(response);
    }

    /* 회원 목록 */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getUserList(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "20", name = "size") int size) {

        String role = "ROLE_USER";
        Pageable pageable = PageRequest.of(page, size);

        Page<User> userList = userManagementService.getAllUsersByRolePaged(role, pageable);

        return createResponse(userList);
    }

    /* 회원 검색 */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchUsers(
            @RequestParam(required = false, name = "group") String group,
            @RequestParam(required = false, name = "keyword") String keyword,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "20", name = "size") int size) {

        String role = "ROLE_USER";
        Pageable pageable = PageRequest.of(page, size);

        Page<User> userList;
        if(group !=null && keyword !=null){
            userList = userManagementService.searchUsersByGroupAndKeyword(group, keyword, role, pageable);
        }else{
             userList = userManagementService.getAllUsersByRolePaged(role, pageable);
        }

        return createResponse(userList);
    }

    /* 회원 탈퇴 */
    @PostMapping("/delete/{uNum}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable("uNum") long uNum){
        Map<String, Object> response = new HashMap<>();
        try {
            // 회원 탈퇴 처리 후 DB에 탈퇴 날짜 업데이트
            userManagementService.updateUserDeleteDate(uNum);

            // Refresh 토큰 정보 삭제
            refreshRepository.deleteRefreshByUsernameWithDelDate();

            response.put("response", "User deleted successfully");
            response.put("time", new Date());
            response.put("status", "Success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("response", "Failed to delete user");
            response.put("time", new Date());
            response.put("status", "Fail");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
