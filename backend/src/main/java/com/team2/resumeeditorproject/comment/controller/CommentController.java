package com.team2.resumeeditorproject.comment.controller;

import com.team2.resumeeditorproject.comment.domain.Comment;
import com.team2.resumeeditorproject.comment.dto.CommentDTO;
import com.team2.resumeeditorproject.comment.service.CommentService;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.repository.ResumeBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ResumeBoardRepository resumeBoardRepository;

    /* 댓글 작성하기 */
    @PostMapping("/write")
    public ResponseEntity<Map<String, Object>> writeComment(@RequestBody CommentDTO commentDTO){
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();
        try{
            ResumeBoard resumeBoard = resumeBoardRepository.findById(commentDTO.getRNum()).orElse(null);
            if (resumeBoard == null) { // 해당하는 게시글이 없다면
                throw new Exception(" - ResumeBoard with num " + commentDTO.getRNum() + " not found");
            }

            if(commentDTO.getCContent().length() > 100){ // 댓글 최대 글자수(100)을 넘으면 예외 발생
                throw new Exception("[Failed to write a comment] Comments must not exceed 100 characters");
            }
            commentService.insertComment(commentDTO);

            response.put("response", "comment table insert success");
            response.put("time", today);
            response.put("status", "Success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            response.put("response", "server error : comment write Fail " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 댓글 삭제 - delete_at 컬럼에 삭제 날짜 추가 */
    @PutMapping("/delete/{c_num}")
    public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable("c_num") Long c_num){
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();
        try{
            int n = commentService.deleteComment(c_num); // delete_at 컬럼 업데이트

            response.put("response", "comment delete success");
            response.put("time", today);
            response.put("status", "Success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            response.put("response", "server error : comment delete Fail ");
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 댓글 수정 */
    @PutMapping("/update/{c_num}")
    public ResponseEntity<Map<String, Object>> updateComment(@PathVariable("c_num") Long c_num, @RequestBody Map<String, Object> requestBody){
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();

        try{
            int n = commentService.updateComment(c_num, (String)requestBody.get("c_content"));
            response.put("response", "comment update success");
            response.put("time", today);
            response.put("status", "Success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            response.put("response", "server error : comment update Fail ");
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 댓글 조회 */
    @GetMapping("/{num}")
    public ResponseEntity<Map<String, Object>> getComments(@PathVariable("num") Long r_num, @RequestParam("page") int page){
        int size = 5; // 한 페이지에 보여줄 댓글 수

        Map<String, Object> response = new HashMap<>();
        Date today = new Date();

        try{
            ResumeBoard resumeBoard = resumeBoardRepository.findById(r_num).orElse(null);
            if (resumeBoard == null) { // 해당하는 게시글이 없다면
                throw new Exception(" - ResumeBoard with num " + r_num + " not found");
            }

            if(page < 0){ // 페이지가 음수인 경우 첫 페이지로 이동하게
                page = 0;
            }

            // 페이지 및 페이지 크기를 기반으로 페이징된 결과를 가져옴
            Pageable pageable = PageRequest.of(page, size);
            Page<Object[]> results = commentService.getComments(r_num, pageable);

            if(page > results.getTotalPages() - 1){ // 페이지 범위를 초과한 경우 마지막 페이지로 이동하게
                page = results.getTotalPages() - 1;
                pageable = PageRequest.of(page, size);
                results = commentService.getComments(r_num, pageable);
            }

            List<Map<String, Object>> formattedResults = new ArrayList<>();
            if(results.getTotalElements() == 0){ // 댓글이 없는 경우
                response.put("response", "댓글이 없습니다.");
            }
            else{
                for (Object[] result : results.getContent()) {
                    Map<String, Object> formattedResult = new HashMap<>();

                    // 첫 번째 요소는 Comment
                    Comment comment = (Comment) result[0];
                    formattedResult.put("c_num", comment.getCNum());
                    formattedResult.put("r_num", comment.getRNum());
                    formattedResult.put("u_num", comment.getUNum());
                    formattedResult.put("c_content", comment.getCContent());
                    formattedResult.put("w_date", comment.getW_date());

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
        }catch (Exception e) {
            response.put("response", "server error : comment list get Fail " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
