package com.team2.resumeeditorproject.comment.controller;

import com.team2.resumeeditorproject.comment.domain.Comment;
import com.team2.resumeeditorproject.comment.dto.CommentDTO;
import com.team2.resumeeditorproject.comment.service.CommentService;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/board/list")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /* 댓글 작성하기 */
    @PostMapping("/comment")
    public ResponseEntity<Map<String, Object>> writeComment(@RequestBody CommentDTO commentDTO){
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();
        try{
            commentService.insertComment(commentDTO);

            response.put("response", "comment table insert success");
            response.put("time", today);
            response.put("status", "Success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            response.put("response", "server error : comment write Fail ");
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
    @GetMapping("/{num}/comment")
    public ResponseEntity<Map<String, Object>> getComments(@PathVariable("num") Long r_num){
        Map<String, Object> response = new HashMap<>();
        Date today = new Date();
        try{
            List<Object[]> results = commentService.getComments(r_num);
            List<Map<String, Object>> formattedResults = new ArrayList<>();

            for (Object[] result : results) {
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
            response.put("time", today);
            response.put("status", "Success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            response.put("response", "server error : comment list get Fail " + e.getMessage());
            response.put("time", today);
            response.put("status", "Fail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
