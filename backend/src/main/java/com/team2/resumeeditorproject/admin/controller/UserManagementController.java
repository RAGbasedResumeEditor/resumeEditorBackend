package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.repository.AdminResumeEditRepository;
import com.team2.resumeeditorproject.admin.repository.AdminUserRepository;
import com.team2.resumeeditorproject.admin.service.UserManagementService;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.RefreshRepository;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import com.team2.resumeeditorproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserManagementController {

    private final UserManagementService userManagementService;
    private final AdminResumeEditRepository adminResumeEditRepository;
    private final RefreshRepository refreshRepository;
    private final UserRepository userRepository;

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
            @RequestParam(defaultValue = "0", name = "page") int page) {

        String role = "ROLE_USER";
        int size = 20;

        // 음수값이라면 0 페이지로 이동
        if(page<0){
            page=0;
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<User> userList = userManagementService.getAllUsersPaged(pageable);

        // page 가 totalPages 보다 높다면 마지막 페이지로 이동
        if(page >= userList.getTotalPages()){
            page=userList.getTotalPages() - 1;
            pageable = PageRequest.of(page, size);
            userList = userManagementService.getAllUsersPaged(pageable);
        }

        return createResponse(userList);
    }

    /* 회원 검색 */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchUsers(
            @RequestParam(required = false, name = "group") String group,
            @RequestParam(required = false, name = "keyword") String keyword,
            @RequestParam(defaultValue = "0", name = "page") int page) {

        String role = "ROLE_USER";
        int size = 20;

        // 음수값이라면 0 페이지로 이동
        if(page<0){
            page=0;
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<User> userList;
        if(group !=null && keyword !=null){
            userList = userManagementService.searchUsersByGroupAndKeyword(group, keyword, pageable);
        }else{
             userList = userManagementService.getAllUsersPaged(pageable);
        }

        // page 가 totalPages 보다 높다면 마지막 페이지로 이동
        if (page >= userList.getTotalPages()) {
            page = userList.getTotalPages() - 1;
            pageable = PageRequest.of(page, size);
            if (group != null && keyword != null) {
                userList = userManagementService.searchUsersByGroupAndKeyword(group, keyword, pageable);
            } else {
                userList = userManagementService.getAllUsersPaged(pageable);
            }
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

            // 해당 사용자의 refresh 토큰 정보 삭제
            User deletedUser = userRepository.findById(uNum)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + uNum));
            refreshRepository.deleteRefreshByUsername(deletedUser.getUsername());

            // 회원의 role을 ROLE_BLACKLIST 로 변경
            deletedUser.setRole("ROLE_BLACKLIST");
            userRepository.save(deletedUser);

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
