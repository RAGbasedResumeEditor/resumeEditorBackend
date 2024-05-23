package com.team2.resumeeditorproject.user.controller;

import com.team2.resumeeditorproject.admin.service.UserManagementService;
import com.team2.resumeeditorproject.resume.domain.Resume;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import com.team2.resumeeditorproject.resume.dto.ResumeEditDTO;
import com.team2.resumeeditorproject.resume.repository.ResumeBoardRepository;
import com.team2.resumeeditorproject.resume.repository.ResumeRepository;
import com.team2.resumeeditorproject.resume.service.ResumeBoardService;
import com.team2.resumeeditorproject.resume.service.ResumeEditService;
import com.team2.resumeeditorproject.resume.service.ResumeService;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.CustomUserDetails;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.RefreshRepository;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import com.team2.resumeeditorproject.user.service.UserService;
import jakarta.servlet.http.HttpServlet;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.*;

import static com.team2.resumeeditorproject.admin.service.ResponseHandler.*;

@Controller
@RequiredArgsConstructor
public class UserController extends HttpServlet {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RefreshRepository refreshRepository;
    private final UserManagementService userManagementService;

    @Autowired
    private ResumeBoardService resumeBoardService;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ResumeEditService resumeEditService;

    @Autowired
    private ModelMapper modelMapper;


    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return userDetails.getUsername();
    }

    //회원가입
    @PostMapping(value = "/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody UserDTO userDto) throws IOException {
        try {
            if (userService.checkUsernameDuplicate(userDto.getUsername())) {
                return createBadReqResponse("이미 존재하는 username 입니다.");
            }
            userService.signup(userDto);//회원가입 처리
            return createResponse("회원가입 성공");
        } catch (Exception e) {
            return createServerErrResponse();
        }
    }//signup()
/*
    @PostMapping("/signup/exists/username")
    public ResponseEntity<Map<String,Object>> checkUsernameDuplicate(HttpServletRequest req) throws AuthenticationException {
             UserDTO userDto=new UserDTO();
            try{
                ObjectMapper objectMapper=new ObjectMapper();
                ServletInputStream inputStream=req.getInputStream();
                String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
                userDto=objectMapper.readValue(messageBody, UserDTO.class);
            }catch(IOException e){
                throw new RuntimeException(e);
            }
            String username=userDto.getUsername();
        
            Map<String,Object> response=new HashMap<>();
            Map<String,Object> errorResponse=new HashMap<>();
            try{
                boolean result=userService.checkUsernameDuplicate(username); // 중복 시 true
                response.put("status","Success");
                response.put("result",result+"");
                response.put("time", new Date());
                response.put("response", "Username 중복 여부 확인 성공");
                return ResponseEntity.ok(response);
            }catch(Exception e){
                errorResponse.put("status","Fail");
                errorResponse.put("time",new Date());
                errorResponse.put("response", "서버 오류입니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
    }

    @PostMapping("/signup/exists/email")
    public ResponseEntity<Map<String,Object>> checkEmailDuplicate(@RequestBody UserDTO userDto){
        Map<String,Object> response=new HashMap<>();
        Map<String,Object> errorResponse=new HashMap<>();
        try{
            boolean result= userService.checkEmailDuplicate(userDto.getEmail());
            response.put("isEmailExists",result);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            errorResponse.put("status","Fail");
            errorResponse.put("time",new Date());
            errorResponse.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }*/

    //회원조회
    @PostMapping("/user/search")
    public ResponseEntity<Map<String, Object>> showUser() {

        String username = getUsername();

        try {
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
            return createResponse(user);
        } catch (Exception e) {
            return createServerErrResponse();
        }
    }

    //회원탈퇴
    @PostMapping("/user/delete")
    public ResponseEntity<Map<String, Object>> deleteUser() throws AuthenticationException {

        String username = getUsername();
        Long uNum = userService.showUser(username).getUNum();

        try {
            // 회원 탈퇴 처리 후 DB에 탈퇴 날짜 업데이트
            userManagementService.updateUserDeleteDate(uNum);

            // 해당 사용자의 refresh 토큰 정보 삭제
            User deletedUser = userRepository.findById(uNum)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + uNum));
            refreshRepository.deleteRefreshByUsername(deletedUser.getUsername());
            ;

            return createResponse(uNum + "번 회원 탈퇴 완료.");
        } catch (Exception e) {
            return createServerErrResponse();
        }
    }

    //회원정보 수정
    @PostMapping("/user/update")
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UserDTO userDto) throws AuthenticationException {
        String username = getUsername();
        User tempUser = userService.showUser(username);
        Long uNum = tempUser.getUNum();

        userDto.setUNum(uNum);

        if (userDto.getBirthDate() == null) {
            userDto.setBirthDate(tempUser.getBirthDate());
        }

        try {
            if (userDto.getPassword() == null) {
                return createBadReqResponse("비밀번호는 반드시 입력해야합니다.");
            }
            userService.updateUser(userDto);//수정 처리
            return createResponse(uNum + "번 회원 수정 완료.");
        } catch (Exception e) {
            return createServerErrResponse();
        }
    }

    // 즐겨찾기 목록 조회
    @GetMapping("user/bookmark")
    public ResponseEntity<Map<String, Object>> showBookmark(@RequestParam("page") int page) throws AuthenticationException {
        String username = getUsername();
        Long uNum = userService.showUser(username).getUNum();
        int size = 5; // 한 페이지에 보여줄 게시글 수
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();

        try{
            // 즐겨찾기한 목록 보여주기
            // 로그인한 user의 u_num과 일치하는 항목을 bookmark 테이블에서 찾고, 그에 해당하는 r_num으로 목록 불러오기
            page = (page < 0) ? 0 : page; // 페이지가 음수인 경우 첫 페이지로 이동하게

            // 페이지 및 페이지 크기를 기반으로 페이징된 결과를 가져옴
            Pageable pageable = PageRequest.of(page, size);
            Page<Object[]> resultsPage = resumeBoardService.getBookmarkList(uNum, pageable);

            if(resultsPage.getTotalElements() == 0){ // 게시글이 없는 경우
                response.put("response", "게시글이 없습니다.");
            }
            else { // 게시글이 있는 경우
                if(page > resultsPage.getTotalPages() - 1){ // 페이지 범위를 초과한 경우 마지막 페이지로 이동하게
                    page = resultsPage.getTotalPages() - 1;
                    pageable = PageRequest.of(page, size);
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

        }catch (Exception e) {
            response.put("response", "server error : bookmark list Fail " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 첨삭 기록 목록 조회
    @GetMapping("user/edit-list")
    public ResponseEntity<Map<String, Object>> resumeEditList(@RequestParam("page") int page) throws AuthenticationException {
        String username = getUsername();
        Long uNum = userService.showUser(username).getUNum();
        int size = 5; // 한 페이지에 보여줄 게시글 수
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();

        try{
            page = (page < 0) ? 0 : page; // 페이지가 음수인 경우 첫 페이지로 이동하게

            // 페이지 및 페이지 크기를 기반으로 페이징된 결과를 가져옴
            Pageable pageable = PageRequest.of(page, size);
            Page<Object[]> resultsPage = resumeService.myPageEditList(uNum, pageable);

            if(resultsPage.getTotalElements() == 0){ // 게시글이 없는 경우
                response.put("response", "게시글이 없습니다.");
            }
            else { // 게시글이 있는 경우
                if(page > resultsPage.getTotalPages() - 1){ // 페이지 범위를 초과한 경우 마지막 페이지로 이동하게
                    page = resultsPage.getTotalPages() - 1;
                    pageable = PageRequest.of(page, size);
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

        }catch (Exception e) {
            response.put("response", "server error : bookmark list Fail " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 첨삭 기록 상세페이지
    @GetMapping("user/edit-list/{num}") // ** re null값인것 처리
    public ResponseEntity<Map<String, Object>> getResumeBoard(@PathVariable("num")  Long num) {
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();
        try {
            Resume resume = resumeRepository.findById(num).orElse(null);
            if (resume == null) { // 해당하는 첨삭 기록이 없다면
                throw new Exception(" - resume with num " + num + " not found");
            }

            // 조회하려는 첨삭기록의 u_num이 로그인한 유저의 u_num과 같은지 확인
            String username = getUsername();
            Long uNum = userService.showUser(username).getUNum();
            if(!uNum.equals(resume.getU_num())){ // 일치하지 않는다면
                throw new Exception(" - 잘못된 접근입니다. (로그인한 사용자의 첨삭 기록이 아닙니다)");
            }


            Object results = resumeService.getResumeEditDetail(num);
            Object[] resultArray = (Object[]) results;
            Map<String, Object> responseData = new HashMap<>();

            // 첫 번째 요소는 ResumeEdit
            ResumeEdit resumeEdit = (ResumeEdit) resultArray[0];
            resumeEditService.setModelMapper(modelMapper); // Setter or Constructor injection
            ResumeEditDTO resumeEditDTO = resumeEditService.convertToDto(resumeEdit);

            responseData.put("r_num", resumeEditDTO.getR_num());
            responseData.put("company", resumeEditDTO.getCompany());
            responseData.put("occupation", resumeEditDTO.getOccupation());
            responseData.put("items", resumeEditDTO.getItems());
            responseData.put("awards", resumeEditDTO.getAwards());
            responseData.put("experience", resumeEditDTO.getExperience());
            responseData.put("options", resumeEditDTO.getOptions());
            responseData.put("r_content", resumeEditDTO.getR_content()); // 첨삭 전 자소서
            responseData.put("mode", resumeEditDTO.getMode());

            // 두 번째 요소는 resume의 content (첨삭 후 자소서)
            String content = (String) resultArray[1];
            responseData.put("content", content);

            // 세 번째 요소는 w_date (첨삭일)
            Date w_date = (Date) resultArray[2];
            responseData.put("w_date", w_date);

            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", "server error " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
