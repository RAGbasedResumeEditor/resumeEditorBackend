package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.service.ResumeManagementService;
import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.team2.resumeeditorproject.admin.service.ResponseHandler.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ResumeManagementController {

    private final ResumeManagementService rmService;

    public static List<ResumeBoardDTO> createRbList(Page<ResumeBoard> rbList) {
        List<ResumeBoardDTO> rbDtoList = new ArrayList<>();
        for (ResumeBoard rb : rbList) {
            ResumeBoardDTO rbDto = new ResumeBoardDTO();
            rbDto.setRating(rb.getRating());
            rbDto.setTitle(rb.getTitle());
            rbDto.setRNum(rb.getRNum());
            rbDto.setRead_num(rb.getRead_num());
            rbDto.setRating_count(rb.getRating_count());
            rbDtoList.add(rbDto);
        }
        return rbDtoList;
    }

    //자소서 목록 가져오기
    @GetMapping("/board/list")
    public ResponseEntity<Map<String, Object>> getAllResumeBoard(@RequestParam(name="page", defaultValue = "0") int page){
           Page<ResumeBoard> rbList = rmService.getResumeBoards(page);
           int totalPage = rbList.getTotalPages();

           if (page > totalPage-1) {
               page = totalPage-1;
               rbList = rmService.getResumeBoards(page);
           }else if(page<0) page=0;

           if (rbList.isEmpty()) {
               return createBadReqResponse("자소서가 존재하지 않습니다.");
           }

           return createPagedResponse(totalPage, createRbList(rbList));
    }

    //자소서 삭제
    @PostMapping("/board/list/delete")
    public ResponseEntity<Map<String, Object>> deleteResume(@RequestBody ResumeBoardDTO rbDto){
            long rNum=rbDto.getRNum();
            if(!rmService.checkResumeExists(rNum)){
                throw new BadRequestException("존재하지 않는 자소서입니다.");
            }

            rmService.deleteResume(rNum);
            return createResponse(rNum+"번 자소서 삭제 성공");
    }
/*
    //자소서 수정
    @PostMapping("/resume/update")
    public ResponseEntity<Map<String, Object>> updateResume(@RequestBody ResumeBoardDTO rbDto){

        Map<String,Object> response=new HashMap<>();
        Map<String,Object> errorResponse1=new HashMap<>();
        Map<String,Object> errorResponse2=new HashMap<>();

        try{   //수정 처리
            if(!rmService.checkResumeExists(rbDto.getRNum())){
                errorResponse1.put("status","Fail");
                errorResponse1.put("time",new Date());
                errorResponse1.put("response", "존재하지 않는 자소서입니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse1);
            }
            rmService.updateResume(rbDto);
            response.put("status","Success");
            response.put("time", new Date());
            response.put("response", rbDto.getRNum()+"번 자소서 수정 완료.");
            return ResponseEntity.ok(response);
        }catch(Exception e){
            errorResponse2.put("status","Fail");
            errorResponse2.put("time",new Date());
            errorResponse2.put("response", "서버 오류입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse2);
        }
    }
*/
    //자소서 검색
    @GetMapping("/board/list/search")
    public ResponseEntity<Map<String, Object>> searchTitle(@RequestParam(name="group", defaultValue = "title") String group,
                                                           @RequestParam(name="title", required = false) String title,
                                                           @RequestParam(value ="rating", required = false, defaultValue = "3") Float rating,
                                                           @RequestParam(name="page", defaultValue = "0") int page){
        Page<ResumeBoard> rbList =null;
        int totalPage=0;

            if(group.equals("title")) {
                rbList = rmService.searchByTitle(title, page);
                totalPage=rbList.getTotalPages();
                if(page>totalPage-1) {
                    page=totalPage-1;
                    rbList=rmService.searchByTitle(title, page);
                }else if(page<0) page=0;

                if(rbList.isEmpty()){
                    throw new BadRequestException("존재하지 않는 자소서입니다.");
                }
            }else if(group.equals("rating")){
                rbList = rmService.searchByRating(rating, page);
                totalPage=rbList.getTotalPages();

                if(page>totalPage-1) {
                    page=totalPage-1;
                    rbList=rmService.searchByRating(rating, page);
                }else if(page<0) page=0;

                if(rbList.isEmpty()){
                    throw new BadRequestException("존재하지 않는 자소서입니다.");
                }
            }//else if
            return createPagedResponse(totalPage,createRbList(rbList));
    }
}
