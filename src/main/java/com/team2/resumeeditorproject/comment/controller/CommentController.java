package com.team2.resumeeditorproject.comment.controller;

import com.team2.resumeeditorproject.comment.dto.CommentDTO;
import com.team2.resumeeditorproject.comment.service.CommentService;
import com.team2.resumeeditorproject.common.dto.response.CommonListResponse;
import com.team2.resumeeditorproject.common.dto.response.ResultMessageResponse;
import com.team2.resumeeditorproject.exception.DataNotFoundException;
import com.team2.resumeeditorproject.resume.repository.ResumeBoardRepository;
import com.team2.resumeeditorproject.resume.service.ResumeBoardService;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ResumeBoardRepository resumeBoardRepository;

    @Autowired
    private ResumeBoardService resumeBoardService;

    private static final int SIZE_OF_PAGE = 5; // 한 페이지에 보여줄 댓글 수

    /* 댓글 작성하기 */
    @PostMapping("/write")
    public ResponseEntity<ResultMessageResponse> writeComment(@RequestBody CommentDTO commentDTO, UserDTO loginUser) {
        if (resumeBoardService.isNotExistResumeBoard(commentDTO.getResumeBoardNo())) {
            throw new DataNotFoundException("ResumeBoard with num " + commentDTO.getResumeBoardNo() + " not found");
        }

        return ResponseEntity.ok()
                .body(ResultMessageResponse.builder()
                        .response(commentService.insertComment(commentDTO, loginUser))
                        .status("Success")
                        .time(new Date())
                        .build());

    }

    /* 댓글 삭제 - delete_at 컬럼에 삭제 날짜 추가 */
    @DeleteMapping("/{commentNo}")
    public ResponseEntity<ResultMessageResponse> deleteComment(@PathVariable("commentNo") Long commentNo) {
        return ResponseEntity.ok()
                .body(ResultMessageResponse.builder()
                        .response(commentService.deleteComment(commentNo))
                        .status("Success")
                        .time(new Date())
                        .build());
    }

    /* 댓글 수정 */
    @PatchMapping("/{commentNo}")
    public ResponseEntity<ResultMessageResponse> updateComment(@PathVariable("commentNo") Long commentNo, @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok()
                .body(ResultMessageResponse.builder()
                        .response(commentService.updateComment(commentNo, commentDTO.getContent()))
                        .status("Success")
                        .time(new Date())
                        .build());
    }

    /* 댓글 조회 */
    @GetMapping("/{resumeBoardNo}")
    public ResponseEntity<CommonListResponse<CommentDTO>> getComments(@PathVariable("resumeBoardNo") Long resumeBoardNo, @RequestParam("pageNo") int pageNo) {
        int size = SIZE_OF_PAGE;

        if (resumeBoardService.isNotExistResumeBoard(resumeBoardNo)) {
            throw new DataNotFoundException("ResumeBoard with num " + resumeBoardNo + " not found");
        }

        // 페이지 및 페이지 크기를 기반으로 페이징된 결과를 가져옴
        Page<CommentDTO> comments = commentService.getPagedComments(resumeBoardNo, pageNo, size);

        return ResponseEntity
                .ok()
                .body(CommonListResponse.<CommentDTO>builder()
                        .status("Success")
                        .response(comments.toList())
                        .totalPages(comments.getTotalPages())
                        .build());

    }
}
