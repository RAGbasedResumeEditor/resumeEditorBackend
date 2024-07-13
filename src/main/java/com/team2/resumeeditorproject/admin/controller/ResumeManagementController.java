package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.dto.response.DeleteResumeResultResponse;
import com.team2.resumeeditorproject.admin.dto.response.ResumeBoardListResponse;
import com.team2.resumeeditorproject.admin.service.ResumeManagementService;
import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/resume")
@RequiredArgsConstructor
public class ResumeManagementController {

    private final ResumeManagementService resumeManagementService;

    private static final int SIZE_OF_PAGE = 10; // 한 페이지에 보여줄 게시글 수

    // 페이지 범위를 초과한 경우 마지막 페이지로 이동하는 메서드
    private Page<ResumeBoardDTO> adjustPageNumberIfOutOfBounds(int pageNo, Page<ResumeBoardDTO> pageResult) {
        int lastPageNo = pageResult.getTotalPages() - 1;
        if (pageNo > lastPageNo && lastPageNo >= 0) { // 마지막 페이지가 유효한 경우에만
            pageNo = lastPageNo;
            Pageable newPageable = PageRequest.of(pageNo, SIZE_OF_PAGE, Sort.by("resumeNo").descending()); // 페이지 번호와 정렬 정보를 포함하여 pageable 다시 생성
            pageResult = resumeManagementService.getPagedResumeBoards(newPageable); // 재호출
        }
        return pageResult;
    }

    //게시글 목록
    @GetMapping("/board/list")
    public ResponseEntity<ResumeBoardListResponse> getPagedAllResumeBoards(
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo) {
        pageNo = (pageNo < 0) ? 0 : pageNo;

        // 페이지 및 페이지 크기를 기반으로 페이징된 결과를 가져옴
        Pageable pageable = PageRequest.of(pageNo, SIZE_OF_PAGE, Sort.by("resumeNo").descending());
        Page<ResumeBoardDTO> resultsPage = resumeManagementService.getPagedResumeBoards(pageable);

        // 페이지 범위를 초과한 경우 마지막 페이지로 이동
        resultsPage = adjustPageNumberIfOutOfBounds(pageNo, resultsPage);

        List<ResumeBoardDTO> resumeBoardList = resultsPage.getContent();
        return ResponseEntity.ok()
                .body(ResumeBoardListResponse.builder()
                        .resumeBoardList(resumeBoardList)
                        .totalPages(resultsPage.getTotalPages())
                        .build());
    }

    //제목으로 자소서 검색
    @GetMapping("/board/list/search/title")
    public ResponseEntity<ResumeBoardListResponse> searchPagedResumeBoardsByTitle(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                                                  @RequestParam(name = "title", required = false) String title) {
        // 기본 페이지 번호 검증 및 수정
        pageNo = (pageNo < 0) ? 0 : pageNo;

        // 제목으로 자소서를 검색하여 DTO 리스트를 가져옴
        Page<ResumeBoardDTO> pageResult = resumeManagementService.searchPagedResumeBoardByTitle(title, pageNo);

        pageResult = adjustPageNumberIfOutOfBounds(pageNo, pageResult);

        return ResponseEntity.ok()
                .body(ResumeBoardListResponse.builder()
                        .resumeBoardList(pageResult.getContent())
                        .totalPages(pageResult.getTotalPages())
                        .build());
    }

    //별점으로 자소서 검색
    @GetMapping("/board/list/search/rating")
    public ResponseEntity<ResumeBoardListResponse> searchPagedResumeBoardsByRating(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                                                   @RequestParam(value = "rating", required = false, defaultValue = "3") Float rating) {
        // 기본 페이지 번호 검증 및 수정
        pageNo = (pageNo < 0) ? 0 : pageNo;

        // 별점으로 자소서를 검색하여 DTO 리스트를 가져옴
        Page<ResumeBoardDTO> pageResult = resumeManagementService.searchPagedResumeBoardByRating(rating, pageNo);

        pageResult = adjustPageNumberIfOutOfBounds(pageNo, pageResult);

        return ResponseEntity.ok()
                .body(ResumeBoardListResponse.builder()
                        .resumeBoardList(pageResult.getContent())
                        .totalPages(pageResult.getTotalPages())
                        .build());
    }

    //자소서 삭제
    @DeleteMapping("/board/{resumeNo}")
    public ResponseEntity<DeleteResumeResultResponse> deleteResume(@PathVariable("resumeNo") Long resumeNo) {
        if(!resumeManagementService.checkResumeExists(resumeNo)){
            throw new BadRequestException("존재하지 않는 자소서입니다.");
        }
        resumeManagementService.deleteResume(resumeNo);
        return ResponseEntity.ok()
                .body(DeleteResumeResultResponse.builder()
                        .response(resumeNo + "번 자소서 삭제 성공")
                        .status("Success")
                        .time(new Date())
                        .build());
    }
}
