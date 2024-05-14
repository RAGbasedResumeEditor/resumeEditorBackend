package com.team2.resumeeditorproject.resume.controller;

import com.team2.resumeeditorproject.resume.domain.Rating;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.dto.RatingDTO;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import com.team2.resumeeditorproject.resume.repository.RatingRepository;
import com.team2.resumeeditorproject.resume.service.RatingService;
import com.team2.resumeeditorproject.resume.service.ResumeBoardService;
import com.team2.resumeeditorproject.resume.repository.ResumeBoardRepository;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

import java.util.*;

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

    /* 게시글 목록 */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAllResumeBoards(@RequestParam("page") int page) {
        int size = 5; // 한 페이지에 보여줄 게시글 수

        Map<String, Object> response = new HashMap<>();
        Date today = new Date();

        try {
            // 페이지 및 페이지 크기를 기반으로 페이징된 결과를 가져옴
            Pageable pageable = PageRequest.of(page, size);
            Page<Object[]> resultsPage = resumeBoardService.getAllResumeBoards(pageable);

            List<Map<String, Object>> formattedResults = new ArrayList<>();

            for (Object[] result : resultsPage.getContent()) {
                Map<String, Object> formattedResult = new HashMap<>();

                // 첫 번째 요소는 ResumeBoard와 Resume의 필드를 포함하는 객체
                ResumeBoard resumeBoard = (ResumeBoard) result[0];
                formattedResult.put("r_num", resumeBoard.getRNum());
                formattedResult.put("rating", (float)Math.round(resumeBoard.getRating() * 10) / 10);
                formattedResult.put("rating_count", resumeBoard.getRating_count());
                formattedResult.put("read_num", resumeBoard.getRead_num());

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
            response.put("time", today);
            response.put("status", "Success");
//            response.put("currentPage", resultsPage.getNumber()); // 현재 페이지
//            response.put("totalItems", resultsPage.getTotalElements()); // 총 게시글 수
            response.put("totalPages", resultsPage.getTotalPages()); // 총 페이지 수

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", "server error");
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
                resumeBoard.setRead_num(resumeBoard.getRead_num()+1);
                resumeBoardRepository.save(resumeBoard);
            } else {
                throw new Exception(" - ResumeBoard with num " + num + " not found");
            }

            Object results = resumeBoardService.getResumeBoard(num);
            Object[] resultArray = (Object[]) results;
            Map<String, Object> responseData = new HashMap<>();

            // 첫 번째 요소는 ResumeBoard
            resumeBoard = (ResumeBoard) resultArray[0];
            responseData.put("r_num", resumeBoard.getRNum());
            responseData.put("rating", (float)Math.round(resumeBoard.getRating() * 10) / 10);
            responseData.put("rating_count", resumeBoard.getRating_count());
            responseData.put("read_num", resumeBoard.getRead_num());
            responseData.put("title", resumeBoard.getTitle());

            // 두 번째 요소는 content
            String content = (String) resultArray[1];
            responseData.put("content", content);

            // 세 번째 요소는 w_date
            Date w_date = (Date) resultArray[2];
            responseData.put("w_date", w_date);

            // 네 번째 요소는 u_num
            long u_num = (long) resultArray[3];
            responseData.put("u_num", u_num);

            // 다섯번째 요소는 username
            String username = userRepository.findUsernameByUNum(u_num);
            responseData.put("username", username);

            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", "server error");
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 게시글 검색 */
    @GetMapping("/list/search")
    public ResponseEntity<Map<String, Object>> search(@RequestParam("keyword") String keyword, @RequestParam("page") int page) {
        int size = 5; // 한 페이지에 보여줄 게시글 수

        Map<String, Object> response = new HashMap<>();
        Date today = new Date();
        try {
            // 페이지 및 페이지 크기를 기반으로 페이징된 결과를 가져옴
            Pageable pageable = PageRequest.of(page, size);
            Page<Object[]> resultsPage = resumeBoardService.searchBoard(keyword, pageable);

            List<Map<String, Object>> formattedResults = new ArrayList<>();

            for (Object[] result : resultsPage.getContent()) {
                Map<String, Object> formattedResult = new HashMap<>();

                // 첫 번째 요소는 ResumeBoard와 Resume의 필드를 포함하는 객체
                ResumeBoard resumeBoard = (ResumeBoard) result[0];
                formattedResult.put("r_num", resumeBoard.getRNum());
                formattedResult.put("rating", (float)Math.round(resumeBoard.getRating() * 10) / 10);
                formattedResult.put("rating_count", resumeBoard.getRating_count());
                formattedResult.put("read_num", resumeBoard.getRead_num());
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
            response.put("time", today);
            response.put("status", "Success");
            response.put("totalPages", resultsPage.getTotalPages()); // 총 페이지 수
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", "server error : search Fail");
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 별점 주기 */
    @PostMapping("/list/{num}/rating")
    public ResponseEntity<Map<String, Object>> setRating(@PathVariable("num") Long num, @RequestBody RatingDTO ratingDTO){
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();
        try{
            Map<String, Object> result = new HashMap<>();

            int isRated = ratingService.ratingCount(ratingDTO.getRNum(), ratingDTO.getUNum());

            if(isRated>0){ // 이미 별점을 줬다면
                System.out.println("이미 별점 줬어");
                throw new Exception(" - 이미 별점을 준 게시글");
            }

            if(ratingDTO.getRating()<0 || ratingDTO.getRating()>5){ // 별점 범위를 벗어나는 경우
                throw new Exception(" - 별점 범위 초과");
            }

            // 별점을 안 준 상태면 rating 테이블에 저장
            Rating rating = new Rating();
            rating.setRNum(ratingDTO.getRNum());
            rating.setUNum(ratingDTO.getUNum());
            rating.setRating(ratingDTO.getRating());
            rating = ratingRepository.save(rating);

            // resume_board의 rating_count(게시글 별점 수) 증가
            // 현재(증가 전) rating_count
            ResumeBoardDTO rbDto = resumeBoardService.getResumeBoardForRating(ratingDTO.getRNum());
            int beforeRatingCount = rbDto.getRating_count();
            float beforeRating = rbDto.getRating();

            // 평균 별점.. (현재 평균 별점*현재 별점 준 사람 수 + 지금 주는 별점)/총 별점 준 사람 수
            float avgRating = (beforeRating*beforeRatingCount + ratingDTO.getRating()) / (beforeRatingCount+1);

            // resume_board의 rating_count, rating 업데이트
            int ratingCountUpdate = resumeBoardService.updateRatingCount(ratingDTO.getRNum(), beforeRatingCount+1, avgRating);

            // 증가한 rating_count 가져오기
            rbDto = resumeBoardService.getResumeBoardForRating(ratingDTO.getRNum());

            float afterRating = (float) Math.round(rbDto.getRating() * 10) /10;

            result.put("rating", afterRating);
            result.put("rating_count", rbDto.getRating_count());

            return new ResponseEntity<>(result, HttpStatus.OK); // 업데이트 된 평균 별점/별점수 반환
        }catch (Exception e) {
            response.put("response", "server error : rating Fail " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
