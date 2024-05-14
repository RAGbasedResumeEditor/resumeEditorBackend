package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.repository.AdminResumeEditRepository;
import com.team2.resumeeditorproject.admin.service.UserManagementService;
import com.team2.resumeeditorproject.resume.dto.ResumeEditDTO;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class UserManagementController {

    private final UserManagementService userManagementService;
    private final AdminResumeEditRepository adminResumeEditRepository;

    public UserManagementController(UserManagementService userManagementService, AdminResumeEditRepository adminResumeEditRepository){
        this.userManagementService = userManagementService;
        this.adminResumeEditRepository = adminResumeEditRepository;
    }

    @GetMapping("/user/list")
    public ResponseEntity<Map<String, Object>> userList() {
        String role = "ROLE_USER";
        List<User> userList = userManagementService.getAllUsersByRole(role);
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

        Map<String, Object> response = new HashMap<>();
        response.put("status", "Success");
        response.put("time", new Date());
        response.put("response", userDTOList);

        return ResponseEntity.ok().body(response);
    }
}
