package com.team2.resumeeditorproject.user.controller;

import com.team2.resumeeditorproject.common.util.CommonResponse;
import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.exception.DelDateException;
import com.team2.resumeeditorproject.exception.NotFoundException;
import com.team2.resumeeditorproject.resume.dto.OccupationDTO;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.dto.response.LoadOccupationResponse;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import com.team2.resumeeditorproject.user.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SignupController {

    private final UserRepository userRepository;
    private final SignupService signupService;

    // 직종 검색하여 가져오기
    @GetMapping("/signup/load")
    public ResponseEntity<LoadOccupationResponse> findOccupationsByName(@RequestParam("occupation") String occupation) {
        List<OccupationDTO> occupationDTOs = signupService.findOccupationsByName(occupation);

        if (occupationDTOs.isEmpty()) {
            throw new NotFoundException("검색 결과가 없습니다.");
        }

        LoadOccupationResponse response = LoadOccupationResponse.builder()
                .occupationList(occupationDTOs)
                .build();

        return ResponseEntity.ok()
                .body(LoadOccupationResponse.builder()
                        .occupationList(occupationDTOs)
                        .build());
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse> signup(@RequestBody UserDTO userDTO) throws IOException {
        String username = userDTO.getUsername();

        //30일 이내에 탈퇴한 회원 예외 처리
        User user = userRepository.findByUsername(username);
        if (user != null && user.getDeletedDate() != null) {
            Date delDate = user.getDeletedDate();
            //삭제한 날짜
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String deleted = dateFormat.format(delDate);
            //회원가입 가능한 날짜
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(delDate);
            calendar.add(Calendar.DAY_OF_MONTH, 30);
            Date newDate = calendar.getTime();
            String available = dateFormat.format(newDate);

            List<String> result = new ArrayList<>();
            result.add(deleted);
            result.add(available);
            throw new DelDateException(result);
        }

        if (signupService.checkUsernameDuplicate(username)) {
            throw new BadRequestException(username + " already exists.");
        }

        signupService.signup(userDTO);

        return ResponseEntity.ok()
                .body(CommonResponse.builder()
                        .response("회원가입 성공")
                        .status("Success")
                        .time(new Date())
                        .build());
    }
}
