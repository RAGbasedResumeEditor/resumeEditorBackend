package com.team2.resumeeditorproject.resume.controller;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import com.team2.resumeeditorproject.resume.service.ResumeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAllResumeBoards() {
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();

        try {
            List<Object[]> results = resumeBoardService.getAllResumeBoards();
            List<Map<String, Object>> formattedResults = new ArrayList<>();

            for (Object[] result : results) {
                Map<String, Object> formattedResult = new HashMap<>();

                // 첫 번째 요소는 ResumeBoard와 Resume의 필드를 포함하는 객체
                ResumeBoard resumeBoard = (ResumeBoard) result[0];
                formattedResult.put("r_num", resumeBoard.getR_num());
                formattedResult.put("rating", resumeBoard.getRating());
                formattedResult.put("read_num", resumeBoard.getRead_num());

                // 두 번째 요소는 Resume의 content
                // *********** title로 바꿔야함
                String title = (String) result[1];
                formattedResult.put("title", title);

                // 세 번째 요소는 Resume의 wdate
                String wdate = (String) result[2];
                formattedResult.put("wdate", wdate);

                // 네 번째 요소는 num
                Long num = (Long) result[3];
                formattedResult.put("num", num);

                formattedResults.add(formattedResult);
            }

            response.put("response", formattedResults);
            response.put("time", today);
            response.put("status", "Success");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", "server error");
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/list/{num}")
    public ResponseEntity<Map<String, Object>> getResumeBoard(@PathVariable("num")  Long num) {
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();
        try {
            Object results = resumeBoardService.getResumeBoard(num);
            response.put("response", results);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", "server error");
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
