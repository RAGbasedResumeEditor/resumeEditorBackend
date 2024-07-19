package com.team2.resumeeditorproject.comment.controller;

import com.team2.resumeeditorproject.comment.domain.Comment;
import com.team2.resumeeditorproject.comment.dto.CommentDTO;
import com.team2.resumeeditorproject.comment.service.CommentService;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.repository.ResumeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ResumeBoardRepository resumeBoardRepository;

    private static final int SIZE_OF_PAGE = 5; // 한 페이지에 보여줄 댓글 수

    /* 댓글 작성하기 */
    @PostMapping("/write")
    public ResponseEntity<Map<String, Object>> writeComment(@RequestBody CommentDTO commentDTO) {
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();
        try {
            ResumeBoard resumeBoard = resumeBoardRepository.findById(commentDTO.getResumeNo()).orElse(null);
            if (resumeBoard == null) { // 해당하는 게시글이 없다면
                throw new Exception(" - ResumeBoard with num " + commentDTO.getResumeNo() + " not found");
            }

            if (commentDTO.getContent().length() > 100) { // 댓글 최대 글자수(100)을 넘으면 예외 발생
                throw new Exception("[Failed to write a comment] Comments must not exceed 100 characters");
            }
            commentService.insertComment(commentDTO);

            response.put("response", "comment table insert success");
            response.put("time", today);
            response.put("status", "Success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", "server error : comment write Fail " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 댓글 삭제 - delete_at 컬럼에 삭제 날짜 추가 */
    @PutMapping("/delete/{commentNo}")
    public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable("commentNo") Long commentNo) {
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();
        try {
            int n = commentService.deleteComment(commentNo); // delete_at 컬럼 업데이트

            response.put("response", "comment delete success");
            response.put("time", today);
            response.put("status", "Success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", "server error : comment delete Fail ");
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 댓글 수정 */
    @PutMapping("/update/{commentNo}")
    public ResponseEntity<Map<String, Object>> updateComment(@PathVariable("commentNo") Long commentNo, @RequestBody Map<String, Object> requestBody) {
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();

        try {
            int n = commentService.updateComment(commentNo, (String)requestBody.get("content"));
            response.put("response", "comment update success");
            response.put("time", today);
            response.put("status", "Success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", "server error : comment update Fail ");
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 댓글 조회 */
    @GetMapping("/{resumeNo}")
    public ResponseEntity<Map<String, Object>> getComments(@PathVariable("resumeNo") Long resumeNo, @RequestParam("pageNo") int pageNo) {
        int size = SIZE_OF_PAGE;

        Map<String, Object> response = new HashMap<>();
        Date today = new Date();

        try {
            ResumeBoard resumeBoard = resumeBoardRepository.findById(resumeNo).orElse(null);
            if (resumeBoard == null) { // 해당하는 게시글이 없다면
                throw new Exception(" - ResumeBoard with num " + resumeNo + " not found");
            }

            pageNo = (pageNo < 0) ? 0 : pageNo; // 페이지가 음수인 경우 첫 페이지로 이동하게

            // 페이지 및 페이지 크기를 기반으로 페이징된 결과를 가져옴
            Pageable pageable = PageRequest.of(pageNo, size);
            Page<Object[]> results = commentService.getComments(resumeNo, pageable);

            if (results.getTotalPages() == 0) { // 댓글이 없는 경우
                response.put("response", "댓글이 없습니다.");
            }
            else{
                if (pageNo > results.getTotalPages() - 1){ // 페이지 범위를 초과한 경우 마지막 페이지로 이동하게
                    pageNo = results.getTotalPages() - 1;
                    pageable = PageRequest.of(pageNo, size);
                    results = commentService.getComments(resumeNo, pageable);
                }
                List<Map<String, Object>> formattedResults = new ArrayList<>();
                for (Object[] result : results.getContent()) {
                    Map<String, Object> formattedResult = new HashMap<>();

                    // 첫 번째 요소는 Comment
                    Comment comment = (Comment) result[0];
                    formattedResult.put("commentNo", comment.getCommentNo());
                    formattedResult.put("resumeNo", comment.getResume().getResumeNo());
                    formattedResult.put("userNo", comment.getUser().getUserNo());
                    formattedResult.put("content", comment.getContent());
                    formattedResult.put("w_date", comment.getCreatedDate());

                    // 두 번째 요소는 User의 username
                    String username = (String) result[1];
                    formattedResult.put("username", username);

                    // 세 번째 요소는 num
                    Long num = (Long) result[2];
                    formattedResult.put("num", num);
                    formattedResults.add(formattedResult);
                }
                response.put("response", formattedResults);
            }
            response.put("time", today);
            response.put("status", "Success");
            response.put("totalPages", results.getTotalPages()); // 총 페이지 수
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", "server error : comment list get Fail " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
