package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.dto.response.ResumeBoardListResponse;
import com.team2.resumeeditorproject.admin.service.ResumeManagementService;
import com.team2.resumeeditorproject.common.util.CommonResponse;
import com.team2.resumeeditorproject.common.util.PageUtil;
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

    //게시글 목록
    @GetMapping("/board")
    public ResponseEntity<ResumeBoardListResponse> getPagedAllResumeBoards(
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo) {
        PageUtil.checkUnderZero(pageNo);

        Pageable pageable = PageRequest.of(pageNo, SIZE_OF_PAGE, Sort.by("resumeNo").descending());
        Page<ResumeBoardDTO> resultsPage = resumeManagementService.getPagedResumeBoards(pageable);

        int lastPageNo = resultsPage.getTotalPages() - 1;
        PageUtil.checkExcessLastPageNo(pageNo, lastPageNo);

        List<ResumeBoardDTO> resumeBoardList = resultsPage.getContent();
        return ResponseEntity.ok()
                .body(ResumeBoardListResponse.builder()
                        .resumeBoardList(resumeBoardList)
                        .totalPage(resultsPage.getTotalPages())
                        .build());
    }

    //제목으로 자소서 검색
    @GetMapping("/board/search/title")
    public ResponseEntity<ResumeBoardListResponse> searchPagedResumeBoardsByTitle(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                                                  @RequestParam(name = "title", required = false) String title) {
        PageUtil.checkUnderZero(pageNo);

        // 제목으로 자소서를 검색하여 DTO 리스트를 가져옴
        Page<ResumeBoardDTO> pageResult = resumeManagementService.searchPagedResumeBoardByTitle(title, pageNo);

        int lastPageNo = pageResult.getTotalPages() - 1;
        PageUtil.checkExcessLastPageNo(pageNo, lastPageNo);

        return ResponseEntity.ok()
                .body(ResumeBoardListResponse.builder()
                        .resumeBoardList(pageResult.getContent())
                        .totalPage(pageResult.getTotalPages())
                        .build());
    }

    //별점으로 자소서 검색
    @GetMapping("/board/search/rating")
    public ResponseEntity<ResumeBoardListResponse> searchPagedResumeBoardsByRating(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                                                   @RequestParam(value = "rating", required = false, defaultValue = "3") Float rating) {
        PageUtil.checkUnderZero(pageNo);

        // 별점으로 자소서를 검색하여 DTO 리스트를 가져옴
        Page<ResumeBoardDTO> pageResult = resumeManagementService.searchPagedResumeBoardByRating(rating, pageNo);

        int lastPageNo = pageResult.getTotalPages() - 1;
        PageUtil.checkExcessLastPageNo(pageNo, lastPageNo);

        return ResponseEntity.ok()
                .body(ResumeBoardListResponse.builder()
                        .resumeBoardList(pageResult.getContent())
                        .totalPage(pageResult.getTotalPages())
                        .build());
    }

    //자소서 삭제
    @DeleteMapping("/{resumeNo}")
    public ResponseEntity<CommonResponse> deleteResume(@PathVariable("resumeNo") Long resumeNo) {
        if(!resumeManagementService.checkResumeExists(resumeNo)){
            throw new BadRequestException("존재하지 않는 자소서입니다.");
        }
        resumeManagementService.deleteResume(resumeNo);
        return ResponseEntity.ok()
                .body(CommonResponse.builder()
                        .response(resumeNo + "번 자소서 삭제 성공")
                        .status("Success")
                        .time(new Date())
                        .build());
    }
}
