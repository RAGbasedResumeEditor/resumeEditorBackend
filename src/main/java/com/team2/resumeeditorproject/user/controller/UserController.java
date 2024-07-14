package com.team2.resumeeditorproject.user.controller;

import com.team2.resumeeditorproject.admin.service.UserManagementService;
import com.team2.resumeeditorproject.common.util.CommonResponse;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.domain.ResumeGuide;
import com.team2.resumeeditorproject.resume.dto.ResumeGuideDTO;
import com.team2.resumeeditorproject.resume.repository.ResumeRepository;
import com.team2.resumeeditorproject.resume.service.ResumeBoardService;
import com.team2.resumeeditorproject.resume.service.ResumeEditService;
import com.team2.resumeeditorproject.resume.service.ResumeGuideService;
import com.team2.resumeeditorproject.resume.service.ResumeService;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.CustomUserDetails;
import com.team2.resumeeditorproject.user.dto.ResumeEditDetailDTO;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.RefreshRepository;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import com.team2.resumeeditorproject.user.service.UserService;
import jakarta.servlet.http.HttpServlet;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.team2.resumeeditorproject.common.util.ResponseHandler.createOkResponse;

@Controller
@RequiredArgsConstructor
public class UserController extends HttpServlet {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RefreshRepository refreshRepository;
    private final UserManagementService userManagementService;
    private final ResumeBoardService resumeBoardService;
    private final ResumeRepository resumeRepository;
    private final ResumeService resumeService;
    private final ResumeEditService resumeEditService;
    private final ModelMapper modelMapper;
    private final ResumeGuideService resumeGuideService;

    private static final int SIZE_OF_PAGE = 5; // 한 페이지에 보여줄 게시글 수

    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    //회원조회
    @PostMapping("/user/search")
    public ResponseEntity<Map<String, Object>> showUser() {

            String username = getUsername();

            User tempUser = userService.showUser(username);
            UserDTO user = new UserDTO();
                user.setUNum(tempUser.getUNum());
                user.setEmail(tempUser.getEmail());
                user.setUsername((tempUser.getUsername()));
                user.setRole(tempUser.getRole());
                user.setAge(tempUser.getAge());
                user.setBirthDate(tempUser.getBirthDate());
                user.setGender(tempUser.getGender());
                user.setCompany(tempUser.getCompany());
                user.setOccupation(tempUser.getOccupation());
                user.setWish(tempUser.getWish());
                user.setStatus(tempUser.getStatus());
                user.setMode(tempUser.getMode());
                user.setInDate(tempUser.getInDate());
                user.setDelDate(tempUser.getDelDate());
            return createOkResponse(user);
    }

    //회원탈퇴
    @DeleteMapping("/user/{uNum}")
    public ResponseEntity<CommonResponse> deleteUser(@PathVariable("uNum") Long uNum) throws AuthenticationException {
        userService.deleteUser(uNum);

        return ResponseEntity.ok()
                .body(CommonResponse.builder()
                        .response("uNum+\"번 회원 탈퇴 완료.")
                        .status("Success")
                        .time(new Date())
                        .build());
    }

    //회원정보 수정
    @PatchMapping("/user")
    public ResponseEntity<CommonResponse> updateUser(@RequestBody UserDTO userDTO) throws AuthenticationException {
        String username = getUsername();
        User tempUser = userService.showUser(username);
        userDTO.setUNum(tempUser.getUNum());

        userService.updateUser(userDTO); // Update process

        return ResponseEntity.ok()
                .body(CommonResponse.builder()
                        .response(username + " 회원 수정 완료.")
                        .status("Success")
                        .time(new Date())
                        .build());
    }

    // 즐겨찾기 목록 조회
    @GetMapping("/user/bookmark")
    public ResponseEntity<Map<String, Object>> showBookmark(@RequestParam("pageNo") int pageNo) throws AuthenticationException {
        String username = getUsername();
        Long uNum = userService.showUser(username).getUNum();
        int size = SIZE_OF_PAGE;
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();

        try{
            // 즐겨찾기한 목록 보여주기
            // 로그인한 user의 u_num과 일치하는 항목을 bookmark 테이블에서 찾고, 그에 해당하는 r_num으로 목록 불러오기
            pageNo = (pageNo < 0) ? 0 : pageNo; // 페이지가 음수인 경우 첫 페이지로 이동하게

            // 페이지 및 페이지 크기를 기반으로 페이징된 결과를 가져옴
            Pageable pageable = PageRequest.of(pageNo, size);
            Page<Object[]> resultsPage = resumeBoardService.getBookmarkList(uNum, pageable);

            if(resultsPage.getTotalElements() == 0){ // 게시글이 없는 경우
                response.put("response", "게시글이 없습니다.");
            }
            else { // 게시글이 있는 경우
                if(pageNo > resultsPage.getTotalPages() - 1){ // 페이지 범위를 초과한 경우 마지막 페이지로 이동하게
                    pageNo = resultsPage.getTotalPages() - 1;
                    pageable = PageRequest.of(pageNo, size);
                    resultsPage = resumeBoardService.getBookmarkList(uNum, pageable);
                }

                List<Map<String, Object>> formattedResults = new ArrayList<>();

                for (Object[] result : resultsPage.getContent()) {
                    Map<String, Object> formattedResult = new HashMap<>();

                    // 첫 번째 요소는 ResumeBoard와 Resume의 필드를 포함하는 객체
                    ResumeBoard resumeBoard = (ResumeBoard) result[0];
                    formattedResult.put("r_num", resumeBoard.getRNum());
                    formattedResult.put("rating", (float) Math.round(resumeBoard.getRating() * 10) / 10);
                    formattedResult.put("rating_count", resumeBoard.getRating_count());
                    formattedResult.put("read_num", resumeBoard.getRead_num());
                    formattedResult.put("title", resumeBoard.getTitle());

                    // 두 번째 요소는 Resume의 content
                    String content = (String) result[1];
                    formattedResult.put("content", content);

                    // 세 번째 요소는 Resume의 w_date
                    Date w_date = (Date) result[2];
                    formattedResult.put("w_date", w_date);

                    formattedResults.add(formattedResult);
                }
                response.put("response", formattedResults);
            }
            response.put("time", today);
            response.put("status", "Success");
            response.put("totalPages", resultsPage.getTotalPages()); // 총 페이지 수

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("response", "server error : bookmark list Fail " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 첨삭 기록 목록 조회
    @GetMapping("/user/edit-list")
    public ResponseEntity<Map<String, Object>> resumeEditList(@RequestParam("pageNo") int pageNo) throws AuthenticationException {
        String username = getUsername();
        Long uNum = userService.showUser(username).getUNum();
        int size = SIZE_OF_PAGE;
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();

        try{
            pageNo = (pageNo < 0) ? 0 : pageNo; // 페이지가 음수인 경우 첫 페이지로 이동하게

            // 페이지 및 페이지 크기를 기반으로 페이징된 결과를 가져옴
            Pageable pageable = PageRequest.of(pageNo, size);
            Page<Object[]> resultsPage = resumeService.myPageEditList(uNum, pageable);

            if(resultsPage.getTotalElements() == 0){ // 게시글이 없는 경우
                response.put("response", "게시글이 없습니다.");
            }
            else { // 게시글이 있는 경우
                if(pageNo > resultsPage.getTotalPages() - 1){ // 페이지 범위를 초과한 경우 마지막 페이지로 이동하게
                    pageNo = resultsPage.getTotalPages() - 1;
                    pageable = PageRequest.of(pageNo, size);
                    resultsPage = resumeService.myPageEditList(uNum, pageable);
                }

                List<Map<String, Object>> formattedResults = new ArrayList<>();

                for (Object[] result : resultsPage.getContent()) {
                    Map<String, Object> formattedResult = new HashMap<>();

                    // 첫 번째 요소 r_num
                    long r_num = (long) result[0];
                    formattedResult.put("r_num", r_num);

                    // 두 번째, 세 번째 요소 company, occupation
                    String title = result[1] + " " + result[2];
                    formattedResult.put("title", title);

                    // 네 번째 요소 mode
                    int mode = (int) result[3];
                    formattedResult.put("mode", mode);

                    // 다섯 번째 요소 w_date
                    Date w_date = (Date) result[4];
                    formattedResult.put("w_date", w_date);

                    formattedResults.add(formattedResult);
                }
                response.put("response", formattedResults);
            }
            response.put("time", today);
            response.put("status", "Success");
            response.put("totalPages", resultsPage.getTotalPages()); // 총 페이지 수

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("response", "server error : bookmark list Fail " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 첨삭 기록 상세페이지
    @GetMapping("/user/edit-list/{num}")
    public ResponseEntity<ResumeEditDetailDTO> getResumeBoard(@PathVariable("num") Long num) {
        String username = getUsername();
        ResumeEditDetailDTO detailDTO = resumeService.getResumeEditDetail(num, username);
        return new ResponseEntity<>(detailDTO, HttpStatus.OK);
    }

    // 자소서 가이드 목록 조회
    @GetMapping("/user/guide-list")
    public ResponseEntity<Map<String, Object>> resumeGuideList(
            @RequestParam("pageNo") int pageNo) throws AuthenticationException {
        String username = getUsername();
        Long uNum = userService.showUser(username).getUNum();
        int size = SIZE_OF_PAGE;
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();

        try {
            pageNo = (pageNo < 0) ? 0 : pageNo; // 페이지가 음수인 경우 첫 페이지로 이동하게

            // 페이지 및 페이지 크기를 기반으로 페이징된 결과를 가져옴
            Pageable pageable = PageRequest.of(pageNo, size);
            Page<ResumeGuide> resultsPage = resumeGuideService.getResumeGuidesByUNum(uNum, pageable);

            if (resultsPage.getTotalElements() == 0) { // 게시글이 없는 경우
                response.put("response", "게시글이 없습니다.");
            } else { // 게시글이 있는 경우
                if (pageNo > resultsPage.getTotalPages() - 1) { // 페이지 범위를 초과한 경우 마지막 페이지로 이동하게
                    pageNo = resultsPage.getTotalPages() - 1;
                    pageable = PageRequest.of(pageNo, size);
                    resultsPage = resumeGuideService.getResumeGuidesByUNum(uNum, pageable);
                }

                List<Map<String, Object>> formattedResults = new ArrayList<>();

                for (ResumeGuide resumeGuide : resultsPage.getContent()) {
                    Map<String, Object> formattedResult = new HashMap<>();

                    // 첫 번째 요소 g_num
                    Long gNum = resumeGuide.getGNum();
                    formattedResult.put("No", gNum); //자소서 번호

                    // 두 번째 요소 company
                    String company = resumeGuide.getCompany();
                    formattedResult.put("company", company);

                    // 세 번째 요소 occupation
                    String occupation = resumeGuide.getOccupation();
                    formattedResult.put("occupation", occupation);

                    formattedResults.add(formattedResult);
                }
                response.put("response", formattedResults);
            }
            response.put("time", today);
            response.put("status", "Success");
            response.put("totalPages", resultsPage.getTotalPages()); // 총 페이지 수

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("response", "server error : guide list Fail [" + e.getMessage() + "]");
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 자소서 가이드 상세 페이지
    @GetMapping("/user/guide-list/{gNum}")
    public ResponseEntity<ResumeGuideDTO> getResumeGuideDetail(@PathVariable("gNum") Long gNum) {
        String username = getUsername();
        ResumeGuideDTO resumeGuideDTO = resumeGuideService.getResumeGuideDetail(gNum, username);
        return new ResponseEntity<>(resumeGuideDTO, HttpStatus.OK);
    }
}
