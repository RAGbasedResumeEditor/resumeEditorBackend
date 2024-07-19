package com.team2.resumeeditorproject.resume.controller;

import com.team2.resumeeditorproject.resume.domain.Bookmark;
import com.team2.resumeeditorproject.resume.domain.ResumeRating;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import com.team2.resumeeditorproject.resume.dto.BookmarkDTO;
import com.team2.resumeeditorproject.resume.dto.RatingDTO;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import com.team2.resumeeditorproject.resume.repository.BookmarkRepository;
import com.team2.resumeeditorproject.resume.repository.RatingRepository;
import com.team2.resumeeditorproject.resume.repository.ResumeBoardRepository;
import com.team2.resumeeditorproject.resume.repository.ResumeEditRepository;
import com.team2.resumeeditorproject.resume.repository.ResumeRepository;
import com.team2.resumeeditorproject.resume.service.BookmarkService;
import com.team2.resumeeditorproject.resume.service.RatingService;
import com.team2.resumeeditorproject.resume.service.ResumeBoardService;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import com.team2.resumeeditorproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * resumeBoardController 자소서 게시판 컨트롤러
 *
 * @author : 안은비
 * @fileName : ResumeBoardController
 * @since : 04/30/24
 */
@RestController
@RequestMapping("/board")
public class ResumeBoardController {

    @Autowired
    private ResumeBoardService resumeBoardService;

    @Autowired
    private ResumeBoardRepository resumeBoardRepository;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private ResumeEditRepository resumeEditRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ResumeRepository resumeRepository;

    private static final int SIZE_OF_PAGE = 5; // 한 페이지에 보여줄 게시글 수

    /* 게시글 목록 */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAllResumeBoards(@RequestParam("pageNo") int pageNo) {
        int size = SIZE_OF_PAGE;

        Map<String, Object> response = new HashMap<>();
        Date today = new Date();

        try {
            pageNo = (pageNo < 0) ? 0 : pageNo; // 페이지가 음수인 경우 첫 페이지로 이동하게

            // 페이지 및 페이지 크기를 기반으로 페이징된 결과를 가져옴
            Pageable pageable = PageRequest.of(pageNo, size);
            Page<Object[]> resultsPage = resumeBoardService.getAllResumeBoards(pageable);

            if (resultsPage.getTotalElements() == 0) { // 게시글이 없는 경우
                response.put("response", "게시글이 없습니다.");
            }
            else { // 게시글이 있는 경우
                if (pageNo > resultsPage.getTotalPages() - 1){ // 페이지 범위를 초과한 경우 마지막 페이지로 이동하게
                    pageNo = resultsPage.getTotalPages() - 1;
                    pageable = PageRequest.of(pageNo, size);
                    resultsPage = resumeBoardService.getAllResumeBoards(pageable);
                }

                List<Map<String, Object>> formattedResults = new ArrayList<>();

                for (Object[] result : resultsPage.getContent()) {
                    Map<String, Object> formattedResult = new HashMap<>();

                    // 첫 번째 요소는 ResumeBoard와 Resume의 필드를 포함하는 객체
                    ResumeBoard resumeBoard = (ResumeBoard) result[0];
                    formattedResult.put("resumeNo", resumeBoard.getResume().getResumeNo());
                    formattedResult.put("rating", (float) Math.round(resumeBoard.getRating() * 10) / 10);
                    formattedResult.put("rating_count", resumeBoard.getRatingCount());
                    formattedResult.put("read_num", resumeBoard.getReadCount());

                    // 두 번째 요소는 Resume_board의 title
                    String title = (String) result[1];
                    formattedResult.put("title", title);

                    // 세 번째 요소는 Resume의 content
                    String content = (String) result[2];
                    formattedResult.put("content", content);

                    // 네 번째 요소는 Resume의 w_date
                    Date w_date = (Date) result[3];
                    formattedResult.put("w_date", w_date);

                    // 다섯 번째 요소는 num - 정렬된 글번호
                    Long num = (Long) result[4];
                    formattedResult.put("num", num);

                    formattedResults.add(formattedResult);
                }
                response.put("response", formattedResults);
            }
            response.put("time", today);
            response.put("status", "Success");
            response.put("totalPages", resultsPage.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", "server error " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 게시글 조회 */
    @GetMapping("/list/{num}")
    public ResponseEntity<Map<String, Object>> getResumeBoard(@PathVariable("num")  Long num) {
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();
        try {
            // 조회수 증가
            ResumeBoard resumeBoard = resumeBoardRepository.findById(num).orElse(null);
            if (resumeBoard != null) {
                resumeBoard.setReadCount(resumeBoard.getReadCount()+1);
                resumeBoardRepository.save(resumeBoard);
            } else { // 해당하는 게시글이 없다면
                throw new Exception(" - ResumeBoard with num " + num + " not found");
            }

            ResumeEdit resumeEdit = resumeEditRepository.findById(num).orElse(null);
            if (resumeEdit == null) {
                throw new Exception(" - ResumeEdit with num " + num + " not found");
            }

            Object results = resumeBoardService.getResumeBoard(num);
            Object[] resultArray = (Object[]) results;
            Map<String, Object> responseData = new HashMap<>();

            // 첫 번째 요소는 ResumeBoard
            resumeBoard = (ResumeBoard) resultArray[0];
            responseData.put("resumeNo", resumeBoard.getResume().getResumeNo());
            responseData.put("rating", (float)Math.round(resumeBoard.getRating() * 10) / 10);
            responseData.put("rating_count", resumeBoard.getRatingCount());
            responseData.put("read_num", resumeBoard.getReadCount());
            responseData.put("title", resumeBoard.getTitle());

            // 두 번째 요소는 content
            String content = (String) resultArray[1];
            responseData.put("content", content);

            // 세 번째 요소는 w_date
            Date w_date = (Date) resultArray[2];
            responseData.put("w_date", w_date);

            // 네 번째 요소는 userNo
            long userNo = (long) resultArray[3];
            responseData.put("userNo", userNo);

            // 다섯번째 요소는 username
            String username = userRepository.findUsernameByUserNo(userNo);
            responseData.put("username", username);

            responseData.put("item", resumeEdit.getCompany().getQuestions());

            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", "server error " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 게시글 검색 */
    @GetMapping("/list/search")
    public ResponseEntity<Map<String, Object>> search(@RequestParam("keyword") String keyword, @RequestParam("pageNo") int pageNo) {
        int size = SIZE_OF_PAGE;

        Map<String, Object> response = new HashMap<>();
        Date today = new Date();
        try {
            pageNo = (pageNo < 0) ? 0 : pageNo; // 페이지가 음수인 경우 첫 페이지로 이동하게

            // 페이지 및 페이지 크기를 기반으로 페이징된 결과를 가져옴
            Pageable pageable = PageRequest.of(pageNo, size);
            Page<Object[]> resultsPage = resumeBoardService.searchBoard(keyword, pageable);

            if(resultsPage.getTotalElements() == 0){ // 검색 게시글이 없는 경우
                response.put("response", "검색 결과가 없습니다.");
            }
            else { // 게시글이 있는 경우
                if (pageNo > resultsPage.getTotalPages() - 1) { // 페이지 범위를 초과한 경우 마지막 페이지로 이동하게
                    pageNo = resultsPage.getTotalPages() - 1;
                    pageable = PageRequest.of(pageNo, size);
                    resultsPage = resumeBoardService.getAllResumeBoards(pageable);
                }

                List<Map<String, Object>> formattedResults = new ArrayList<>();

                for (Object[] result : resultsPage.getContent()) {
                    Map<String, Object> formattedResult = new HashMap<>();

                    // 첫 번째 요소는 ResumeBoard와 Resume의 필드를 포함하는 객체
                    ResumeBoard resumeBoard = (ResumeBoard) result[0];
                    formattedResult.put("resumeNo", resumeBoard.getResume().getResumeNo());
                    formattedResult.put("rating", (float) Math.round(resumeBoard.getRating() * 10) / 10);
                    formattedResult.put("rating_count", resumeBoard.getRatingCount());
                    formattedResult.put("read_num", resumeBoard.getReadCount());
                    formattedResult.put("title", resumeBoard.getTitle());

                    // 두 번째 요소는 Resume의 content
                    String content = (String) result[1];
                    formattedResult.put("content", content);

                    // 세 번째 요소는 Resume의 w_date
                    Date w_date = (Date) result[2];
                    formattedResult.put("w_date", w_date);

                    // 네 번째 요소는 num
                    Long num = (Long) result[3];
                    formattedResult.put("num", num);
                    formattedResults.add(formattedResult);
                }
                response.put("response", formattedResults);
            }
            response.put("time", today);
            response.put("status", "Success");
            response.put("totalPages", resultsPage.getTotalPages()); // 총 페이지 수
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", "server error : search Fail " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 별점 주기 */
    @PostMapping("/rating")
    public ResponseEntity<Map<String, Object>> setRating(@RequestBody RatingDTO ratingDTO) {
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();
        try {
            long resumeBoardNo = ratingDTO.getResumeBoardNo();

            ResumeBoard resumeBoard = resumeBoardRepository.findById(resumeBoardNo).orElse(null);
            if (resumeBoard == null) { // 해당하는 게시글이 없다면
                throw new Exception(" - ResumeBoard with num " + resumeBoardNo + " not found");
            }

            Map<String, Object> result = new HashMap<>();

            int isRated = ratingService.ratingCount(ratingDTO.getResumeBoardNo(), ratingDTO.getUserNo());

            if (isRated > 0) { // 이미 별점을 줬다면
                throw new Exception(" - 이미 별점을 준 게시글");
            }

            if (ratingDTO.getRating() < 0 || ratingDTO.getRating() > 5){ // 별점 범위를 벗어나는 경우
                throw new Exception(" - 별점 범위 초과");
            }

            // 별점을 안 준 상태면 rating 테이블에 저장
            ResumeRating resumeRating = ResumeRating.builder()
                    .resumeBoard(resumeBoardRepository.findById(ratingDTO.getResumeBoardNo()).orElseThrow(() -> new IllegalArgumentException("invalid resumeBoardNo")))
                    .user(userRepository.findById(ratingDTO.getUserNo()).orElseThrow(() -> new IllegalArgumentException("invalid userNo")))
                    .rating(ratingDTO.getRating())
                    .build();

            ratingRepository.save(resumeRating);

            // resume_board의 rating_count(게시글 별점 수) 증가
            // 현재(증가 전) rating_count
            ResumeBoardDTO resumeBoardDTO = resumeBoardService.getResumeBoardForRating(ratingDTO.getResumeBoardNo());
            int beforeRatingCount = resumeBoardDTO.getRatingCount();
            float beforeRating = resumeBoardDTO.getRating();

            // 평균 별점.. (현재 평균 별점*현재 별점 준 사람 수 + 지금 주는 별점)/총 별점 준 사람 수
            float avgRating = (beforeRating * beforeRatingCount + ratingDTO.getRating()) / (beforeRatingCount + 1);

            // resume_board의 rating_count, rating 업데이트
            int ratingCountUpdate = resumeBoardService.updateRatingCount(ratingDTO.getResumeBoardNo(), beforeRatingCount+1, avgRating);

            // 증가한 rating_count 가져오기
            resumeBoardDTO = resumeBoardService.getResumeBoardForRating(ratingDTO.getResumeBoardNo());

            float afterRating = (float) Math.round(resumeBoardDTO.getRating() * 10) /10;

            result.put("rating", afterRating);
            result.put("rating_count", resumeBoardDTO.getRatingCount());

            return new ResponseEntity<>(result, HttpStatus.OK); // 업데이트 된 평균 별점/별점수 반환
        } catch (Exception e) {
            response.put("response", "server error : rating Fail " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 별점 확인 */
    @GetMapping("/rating/{num}")
    public ResponseEntity<Map<String, Object>> getRating(@PathVariable("num") Long num, UserDTO loginUser) {
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();
        String username = loginUser.getUsername();
        Long userNo = userService.showUser(username).getUserNo();
        float result = 0;

        try {
            ResumeBoard resumeBoard = resumeBoardRepository.findById(num).orElse(null);
            if (resumeBoard == null) { // 해당하는 게시글이 없다면
                throw new Exception(" - ResumeBoard with num " + num + " not found");
            }

            // 별점 여부 확인
            ResumeRating resumeRating = ratingRepository.findByResumeBoardResumeBoardNoAndUserUserNo(num, userNo);

            if(resumeRating == null){
                result = 0; // 별점 안줬음
            }
            else{
                result = resumeRating.getRating(); // 별점줬음
            }

            response.put("response", result);
            response.put("time", today);
            response.put("status", "Success");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", "server error " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /* 게시글 랭킹 */
    @GetMapping("/list/rank")
    public ResponseEntity<Map<String, Object>> getBoardRanking(@RequestParam("group") String group) {
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();

        try {
            List<Object[]> resultsPage = null;

            if (group.equals("read_num")) {
                resultsPage = resumeBoardService.getBoardRankingReadNum();
            }
            else if (group.equals("rating")) {
                resultsPage = resumeBoardService.getBoardRankingRating();
            }
            else { // read_num 또는 rating이 아닌 경우
                throw new Exception("랭킹 정렬 조건은 read_num 또는 rating만 가능합니다.");
            }
            List<Map<String, Object>> formattedResults = new ArrayList<>();

            for (Object[] result : resultsPage) {
                Map<String, Object> formattedResult = new HashMap<>();

                // 첫 번째 요소는 ResumeBoard와 Resume의 필드를 포함하는 객체
                ResumeBoard resumeBoard = (ResumeBoard) result[0];
                formattedResult.put("resumeNo", resumeBoard.getResume().getResumeNo());
                formattedResult.put("rating", (float) Math.round(resumeBoard.getRating() * 10) / 10);
                formattedResult.put("rating_count", resumeBoard.getRatingCount());
                formattedResult.put("read_num", resumeBoard.getReadCount());

                // 두 번째 요소는 Resume_board의 title
                String title = (String) result[1];
                formattedResult.put("title", title);

                // 세 번째 요소는 Resume의 content
                String content = (String) result[2];
                formattedResult.put("content", content);

                // 네 번째 요소는 Resume의 w_date
                Date w_date = (Date) result[3];
                formattedResult.put("w_date", w_date);

                formattedResults.add(formattedResult);
            }
            response.put("response", formattedResults);
            response.put("time", today);
            response.put("status", "Success");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", "server error " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 즐겨찾기 */
    @PostMapping("/bookmark")
    public ResponseEntity<Map<String, Object>> setBookmark(@RequestBody BookmarkDTO bookmarkDTO) {
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();
        try{
            ResumeBoard resumeBoard = resumeBoardRepository.findById(bookmarkDTO.getResumeBoardNo()).orElse(null);
            if (resumeBoard == null) { // 해당하는 게시글이 없다면
                throw new Exception(" - ResumeBoard with num " + bookmarkDTO.getResumeBoardNo() + " not found");
            }

            // 즐겨찾기를 누르면 테이블에 저장
            // 즐겨찾기를 이미 눌렀으면(테이블에 이미 있으면) -> 삭제(즐겨찾기 취소)
            String result = bookmarkService.bookmarkBoard(bookmarkDTO);

            response.put("response", result);
            response.put("time", today);
            response.put("status", "Success");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", "server error " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /* 즐겨찾기 확인 */
    @GetMapping("/bookmark/{resumeBoardNo}")
    public ResponseEntity<Map<String, Object>> getBookmark(@PathVariable("resumeBoardNo")  Long resumeBoardNo, UserDTO loginUser) {
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();
        String username = loginUser.getUsername();
        Long userNo = userService.showUser(username).getUserNo();
        String result = "";

        try {
            ResumeBoard resumeBoard = resumeBoardRepository.findById(resumeBoardNo).orElse(null);
            if (resumeBoard == null) { // 해당하는 게시글이 없다면
                throw new Exception(" - ResumeBoard with num " + resumeBoardNo + " not found");
            }
            
            // 즐겨찾기 여부 확인
            Bookmark bookmark = bookmarkRepository.findByResumeBoardResumeBoardNoAndUserUserNo(resumeBoardNo, userNo);

            if (bookmark == null) {
                result = "false"; // 즐겨찾기 안했음
            }
            else {
                result = "true"; // 이미 즐겨찾기 했음
            }

            response.put("response", result);
            response.put("time", today);
            response.put("status", "Success");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", "server error " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
