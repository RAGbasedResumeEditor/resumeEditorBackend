package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.service.ResumeManagementService;
import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.team2.resumeeditorproject.admin.service.ResponseHandler.createOkResponse;
import static com.team2.resumeeditorproject.admin.service.ResponseHandler.createPagedResponse;

@Controller
@RequestMapping("/admin/resume")
@RequiredArgsConstructor
public class ResumeManagementController {

    private final ResumeManagementService resumeManagementService;

    private static final int SIZE_OF_PAGE = 10; // 한 페이지에 보여줄 게시글 수

    static List<ResumeBoardDTO> createResumeBoardList(Page<ResumeBoard> resumeBoardList) {
        List<ResumeBoardDTO> resumeBoardDTOList = new ArrayList<>();
        for (ResumeBoard resumeBoard : resumeBoardList) {
            ResumeBoardDTO resumeBoardDTO = new ResumeBoardDTO();
            resumeBoardDTO.setRating(resumeBoard.getRating());
            resumeBoardDTO.setTitle(resumeBoard.getTitle());
            resumeBoardDTO.setRNum(resumeBoard.getRNum());
            resumeBoardDTO.setRead_num(resumeBoard.getRead_num());
            resumeBoardDTO.setRating_count(resumeBoard.getRating_count());
            resumeBoardDTOList.add(resumeBoardDTO);
        }
        return resumeBoardDTOList;
    }

    /* 게시글 목록 */
    @GetMapping("/board/list")
    public ResponseEntity<Map<String, Object>> getAllResumeBoards(@RequestParam("pageNo") int pageNo) {
        int size = SIZE_OF_PAGE;

        Map<String, Object> response = new HashMap<>();

        pageNo = (pageNo < 0) ? 0 : pageNo;

        // 페이지 및 페이지 크기를 기반으로 페이징된 결과를 가져옴
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by("RNum").descending());
        Page<ResumeBoardDTO> resultsPage = resumeManagementService.getPagedResumeBoards(pageable);

        if (resultsPage.getTotalElements() == 0) {
            response.put("response", "게시글이 없습니다.");
        }

        int lastPageNo = resultsPage.getTotalPages() - 1;
        if (pageNo > lastPageNo) { // 페이지 범위를 초과한 경우 마지막 페이지로 이동
            pageNo = lastPageNo;
            pageable = PageRequest.of(pageNo, size);
            resultsPage = resumeManagementService.getPagedResumeBoards(pageable);
        }

        List<ResumeBoardDTO> resumeBoardList = resultsPage.getContent();

        response.put("response", resumeBoardList);
        response.put("totalPages", resultsPage.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //자소서 삭제
    @DeleteMapping("/board/{rNum}")
    public ResponseEntity<Map<String, Object>> deleteResume(@PathVariable("rNum") Long rNum) {
            if(!resumeManagementService.checkResumeExists(rNum)){
                throw new BadRequestException("존재하지 않는 자소서입니다.");
            }
            resumeManagementService.deleteResume(rNum);
            return createOkResponse(rNum + "번 자소서 삭제 성공");
    }

    //자소서 검색
    @GetMapping("/board/list/search")
    public ResponseEntity<Map<String, Object>> searchTitle(@RequestParam(name = "group", defaultValue = "title") String group,
                                                           @RequestParam(name = "title", required = false) String title,
                                                           @RequestParam(value = "rating", required = false, defaultValue = "3") Float rating,
                                                           @RequestParam(name = "pageNo", defaultValue = "0") int pageNo) {
        Page<ResumeBoard> resumeBoardList = null;
        int totalPage = 0;

        if (group.equals("title")) {
            resumeBoardList = resumeManagementService.searchByTitle(title, pageNo);
            totalPage = resumeBoardList.getTotalPages();
            if (pageNo > totalPage -1) {
                pageNo = totalPage -1;
                resumeBoardList = resumeManagementService.searchByTitle(title, pageNo);
            }

            if (resumeBoardList.isEmpty()) {
                throw new BadRequestException("존재하지 않는 자소서입니다.");
            }
        } else if(group.equals("rating")) {
            resumeBoardList = resumeManagementService.searchByRating(rating, pageNo);
            totalPage=resumeBoardList.getTotalPages();

            if (pageNo > totalPage -1) {
                pageNo = totalPage -1;
                resumeBoardList = resumeManagementService.searchByRating(rating, pageNo);
            }

            if (resumeBoardList.isEmpty()) {
                throw new BadRequestException("존재하지 않는 자소서입니다.");
            }
        }
        return createPagedResponse(totalPage,createResumeBoardList(resumeBoardList));
    }
}
