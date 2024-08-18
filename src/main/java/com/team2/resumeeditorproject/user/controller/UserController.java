package com.team2.resumeeditorproject.user.controller;

import com.team2.resumeeditorproject.bookmark.dto.BookmarkDTO;
import com.team2.resumeeditorproject.bookmark.service.BookmarkService;
import com.team2.resumeeditorproject.common.dto.response.CommonListResponse;
import com.team2.resumeeditorproject.common.dto.response.CommonResponse;
import com.team2.resumeeditorproject.resume.domain.ResumeGuide;
import com.team2.resumeeditorproject.resume.dto.ResumeEditDTO;
import com.team2.resumeeditorproject.resume.dto.ResumeGuideDTO;
import com.team2.resumeeditorproject.resume.service.ResumeBoardService;
import com.team2.resumeeditorproject.resume.service.ResumeEditService;
import com.team2.resumeeditorproject.resume.service.ResumeGuideService;
import com.team2.resumeeditorproject.resume.service.ResumeService;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.ResumeEditDetailDTO;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.service.UserService;
import jakarta.servlet.http.HttpServlet;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
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
    private final ResumeBoardService resumeBoardService;
    private final BookmarkService bookmarkService;
    private final ResumeService resumeService;
    private final ResumeEditService resumeEditService;
    private final ResumeGuideService resumeGuideService;

    private static final int SIZE_OF_PAGE = 5; // 한 페이지에 보여줄 게시글 수

    //회원조회
    @PostMapping("/user/search")
    public ResponseEntity<Map<String, Object>> showUser(UserDTO loginUser) {

        String username = loginUser.getUsername();

        User tempUser = userService.showUser(username);
        UserDTO user = new UserDTO();
        user.setUserNo(tempUser.getUserNo());
        user.setEmail(tempUser.getEmail());
        user.setUsername((tempUser.getUsername()));
        user.setRole(tempUser.getRole());
        user.setAge(tempUser.getAge());
        user.setBirthDate(tempUser.getBirthDate());
        user.setGender(tempUser.getGender());
        user.setCompanyNo(tempUser.getCompany().getCompanyNo());
        user.setCompanyName(tempUser.getCompany().getCompanyName());
        user.setOccupationNo(tempUser.getOccupation().getOccupationNo());
        user.setOccupationName(tempUser.getOccupation().getOccupationName());
        user.setWishCompanyNo(tempUser.getWishCompany().getCompanyNo());
        user.setWishCompanyName(tempUser.getWishCompany().getCompanyName());
        user.setStatus(tempUser.getStatus());
        user.setMode(tempUser.getMode());
        user.setCreatedDate(tempUser.getCreatedDate());
        user.setDeletedDate(tempUser.getDeletedDate());
        return createOkResponse(user);
    }

    //회원탈퇴
    @DeleteMapping("/user")
    public ResponseEntity<CommonResponse> deleteUser(UserDTO loginUser) throws AuthenticationException {
        userService.deleteUser(loginUser.getUserNo());

        return ResponseEntity.ok()
                .body(CommonResponse.builder()
                        .response(loginUser.getUserNo() + "번 회원 탈퇴 완료.")
                        .status("Success")
                        .time(new Date())
                        .build());
    }

    //회원정보 수정
    @PatchMapping("/user")
    public ResponseEntity<CommonResponse> updateUser(@RequestBody UserDTO userDTO, UserDTO loginUser) throws AuthenticationException {
        String username = loginUser.getUsername();
        User tempUser = userService.showUser(username);
        userDTO.setUserNo(tempUser.getUserNo());

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
    public ResponseEntity<CommonListResponse<BookmarkDTO>> bookmarkList(@RequestParam("pageNo") int pageNo, UserDTO loginUser) throws AuthenticationException {
        int size = SIZE_OF_PAGE;

        Page<BookmarkDTO> bookmarks = bookmarkService.getBookmarkList(loginUser.getUserNo(), pageNo, size);

        return ResponseEntity
                .ok()
                .body(CommonListResponse.<BookmarkDTO>builder()
                        .status("Success")
                        .response(bookmarks.toList())
                        .totalPages(bookmarks.getTotalPages())
                        .build());
    }

    // 첨삭 기록 목록 조회
    @GetMapping("/user/edit-list")
    public ResponseEntity<CommonListResponse<ResumeEditDTO>> resumeEditList(@RequestParam("pageNo") int pageNo, UserDTO loginUser) throws AuthenticationException {
        int size = SIZE_OF_PAGE;

        Page<ResumeEditDTO> resumeEdits = resumeEditService.myPageEditList(loginUser.getUserNo(), pageNo, size);

        return ResponseEntity
                .ok()
                .body(CommonListResponse.<ResumeEditDTO>builder()
                        .status("Success")
                        .response(resumeEdits.toList())
                        .totalPages(resumeEdits.getTotalPages())
                        .build());
    }

    // 첨삭 기록 상세페이지
    @GetMapping("/user/edit-list/{resumeEditNo}")
    public ResponseEntity<ResumeEditDetailDTO> getResumeBoard(@PathVariable("resumeEditNo") Long resumeEditNo, UserDTO loginUser) {
        String username = loginUser.getUsername();
        ResumeEditDetailDTO detailDTO = resumeService.getResumeEditDetail(resumeEditNo, username);
        return new ResponseEntity<>(detailDTO, HttpStatus.OK);
    }

    // 자소서 가이드 목록 조회
    @GetMapping("/user/guide-list")
    public ResponseEntity<Map<String, Object>> resumeGuideList(@RequestParam("pageNo") int pageNo, UserDTO loginUser) throws AuthenticationException {
        String username = loginUser.getUsername();
        Long userNo = userService.showUser(username).getUserNo();
        int size = SIZE_OF_PAGE;
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();

        try {
            pageNo = (pageNo < 0) ? 0 : pageNo; // 페이지가 음수인 경우 첫 페이지로 이동하게

            // 페이지 및 페이지 크기를 기반으로 페이징된 결과를 가져옴
            Pageable pageable = PageRequest.of(pageNo, size);
            Page<ResumeGuide> resultsPage = resumeGuideService.getResumeGuidesByUserNo(userNo, pageable);

            if (resultsPage.getTotalElements() == 0) { // 게시글이 없는 경우
                response.put("response", "게시글이 없습니다.");
            } else { // 게시글이 있는 경우
                if (pageNo > resultsPage.getTotalPages() - 1) { // 페이지 범위를 초과한 경우 마지막 페이지로 이동하게
                    pageNo = resultsPage.getTotalPages() - 1;
                    pageable = PageRequest.of(pageNo, size);
                    resultsPage = resumeGuideService.getResumeGuidesByUserNo(userNo, pageable);
                }

                List<Map<String, Object>> formattedResults = new ArrayList<>();

                for (ResumeGuide resumeGuide : resultsPage.getContent()) {
                    Map<String, Object> formattedResult = new HashMap<>();

                    // 첫 번째 요소 resumeGuideNo
                    Long resumeGuideNo = resumeGuide.getResumeGuideNo();
                    formattedResult.put("No", resumeGuideNo); //자소서 번호

                    // 두 번째 요소 company
                    String company = resumeGuide.getCompany().getCompanyName();
                    formattedResult.put("company", company);

                    // 세 번째 요소 occupation
                    String occupation = resumeGuide.getOccupation().getOccupationName();
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
    @GetMapping("/user/guide-list/{resumeGuideNo}")
    public ResponseEntity<ResumeGuideDTO> getResumeGuideDetail(@PathVariable("resumeGuideNo") Long gNum, UserDTO loginUser) {
        String username = loginUser.getUsername();
        ResumeGuideDTO resumeGuideDTO = resumeGuideService.getResumeGuideDetail(gNum, username);
        return new ResponseEntity<>(resumeGuideDTO, HttpStatus.OK);
    }
}
