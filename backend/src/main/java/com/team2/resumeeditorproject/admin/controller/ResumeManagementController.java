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
// TODO : import할 때 와일드카드는 사용하지 않는 것이 좋음
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.team2.resumeeditorproject.admin.service.ResponseHandler.*;

@Controller
// TODO : mapping과 클래스명은 연관성이 있어야 함
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ResumeManagementController {

    private final ResumeManagementService rmService;

    // TODO : public static으로 선언해야 할 이유가 없어보임
    // TODO : ResumeBoard를 rb로 줄이는 것은 적절하지 않음
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

    // TODO : 불필요한 주석이나 미구현 기능을 머징하는 것은 지양
    /*자소서 목록 가져오기
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
    }*/


    // TODO : parameter, return 결과는 DTO로 전달하는 것이 좋음
    // TODO : 마찬가지로 Page<Object[]> 같은 선언은 지양되어야 함
    // TODO : page 보다는 pageNo 같이 명확한 명칭이 좋아보임
    // TODO : 메서드 전반에서 불필요한 주석은 제거
    /* 게시글 목록 */
    @GetMapping("/board/list")
    public ResponseEntity<Map<String, Object>> getAllResumeBoards(@RequestParam("page") int page) {
        // TODO : 어떤 size인지 명확한 명칭을 붙이는게 좋고, 상수는 SIZE_OF_PAGE, NUMBER_OF_PAGE 처럼 상수화
        int size = 10; // 한 페이지에 보여줄 게시글 수

        Map<String, Object> response = new HashMap<>();
            // TODO : 들여쓰기가 잘못됨
            page = (page < 0) ? 0 : page; // 페이지가 음수인 경우 첫 페이지로 이동하게

            // 페이지 및 페이지 크기를 기반으로 페이징된 결과를 가져옴
            Pageable pageable = PageRequest.of(page, size, Sort.by("RNum").descending());
            // TODO : 페이징 처리된 결과를 가져오는데 getAllResumeBoards라는 명칭은 맞지 않아보임
            Page<Object[]> resultsPage = rmService.getAllResumeBoards(pageable);
            // TODO : if ~ else 를 사용하지 않고, 게시물이 없으면 적절한 응답을 추가해서 바로 리턴하고 else 문에 해당하는 것은 한 depth 내려서 작성하는 것이 깔끔함
            // TODO : resultsPage.getTotalElements() == 0 대신 .isEmpty()를 사용하는 것이 목적에 맞아보임
            if(resultsPage.getTotalElements() == 0){ // 게시글이 없는 경우
                response.put("response", "게시글이 없습니다.");
            }
            else { // 게시글이 있는 경우
                // TODO : resultsPage.getTotalPages() - 1 는 lastPageNo 로 분리하는 것이 의미상 좋아보임
                // TODO : if () { 공백은 지키는 것이 좋음
                // TODO : 의도는 알겠으나 페이지 범위를 초과한 경우 페이지 범위를 초과했다는 응답을 주는것이 맞아보이고 화면에서 재조회를 유도하는 것이 좋아보임
                if(page > resultsPage.getTotalPages() - 1){ // 페이지 범위를 초과한 경우 마지막 페이지로 이동하게
                    page = resultsPage.getTotalPages() - 1;
                    pageable = PageRequest.of(page, size);
                    resultsPage = rmService.getAllResumeBoards(pageable);
                }

                List<Map<String, Object>> formattedResults = new ArrayList<>();

                // TODO : result라는 이름보다는 명확한 이름을 부여
                // TODO : Object[]를 가져왔기 때문에 캐스팅하는건데 service단에서 DTO로 해석해서 가져오는게 맞아보임 그렇게되면 아래 result[0], result[1] 같은것도 제거될듯
                for (Object[] result : resultsPage.getContent()) {
                    Map<String, Object> formattedResult = new HashMap<>();

                    //
                    // 첫 번째 요소는 ResumeBoard와 Resume의 필드를 포함하는 객체
                    ResumeBoard resumeBoard = (ResumeBoard) result[0];
                    formattedResult.put("r_num", resumeBoard.getRNum());
                    formattedResult.put("rating", (float) Math.round(resumeBoard.getRating() * 10) / 10);
                    formattedResult.put("rating_count", resumeBoard.getRating_count());
                    formattedResult.put("read_num", resumeBoard.getRead_num());

                    // 두 번째 요소는 Resume_board의 title
                    String title = (String) result[1];
                    formattedResult.put("title", title);

                    // 네 번째 요소는 Resume의 w_date
                    Date w_date = (Date) result[2];
                    formattedResult.put("w_date", w_date);

                    String username=(String)result[3];
                    formattedResult.put("username",username);

                    formattedResults.add(formattedResult);
                }
                response.put("response", formattedResults);
            }
            response.put("status", "Success");
            response.put("totalPages", resultsPage.getTotalPages()); // 총 페이지 수

            return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // TODO : 삭제요청은 @DeleteMapping으로 주는 것이 맞아보이고, url에 delete라는 행위를 적는 것은 적절하지 않음 add, update, modify, remove, delete 등 모두 마찬가지임
    //자소서 삭제
    @PostMapping("/board/list/delete")
    public ResponseEntity<Map<String, Object>> deleteResume(@RequestBody ResumeBoardDTO rbDto){
            long rNum=rbDto.getRNum();
            if(!rmService.checkResumeExists(rNum)){
                // TODO : exception을 던지는 것 자체는 좋은 방법인데 던진 exception을 처리할 클래스를 만들어줘야 함
                // TODO : 살펴보기로 BadRequestException를 처리하는 곳이 없어보이는데 그렇다면 403, 405 같은 응답을 주는 것이 맞아보임
                throw new BadRequestException("존재하지 않는 자소서입니다.");
            }

            rmService.deleteResume(rNum);
            return createResponse(rNum+"번 자소서 삭제 성공");
    }

    // TODO : 마찬가지로 미구현된 기능은 제거
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
    // TODO : 검색 api가 management 안에 있는 이유가 있는지?
    @GetMapping("/board/list/search")
    public ResponseEntity<Map<String, Object>> searchTitle(@RequestParam(name="group", defaultValue = "title") String group,
                                                           @RequestParam(name="title", required = false) String title,
                                                           @RequestParam(value ="rating", required = false, defaultValue = "3") Float rating,
                                                           @RequestParam(name="page", defaultValue = "0") int page){
        Page<ResumeBoard> rbList =null;
        int totalPage=0;
            // TODO if () {, } else if () { 공백은 맞춰야 함
            // TODO : else if(page<0)에서 한줄짜리 로직이더라도 중괄호를 감싸는 것을 지향
            // TODO : 들여쓰기가 잘못됨
            // TODO : group이 title도 아니고 rating도 아니라면 createRbList에 null이 전달되는 것으로 보임
            // TODO : 앞서 언급한것처럼 rb, rm등 약어는 사용하지 않는 것이 좋음
            // TODO : =, <, > 같은 기호 양 옆에는 공백을 추가하는 것이 좋음
            if(group.equals("title")) {
                rbList = rmService.searchByTitle(title, page);
                totalPage=rbList.getTotalPages();
                if(page>totalPage-1) {
                    page=totalPage-1;
                    rbList=rmService.searchByTitle(title, page);
                }else if(page<0) page=0; //TODO : page가 0으로 변경된 이후에 page를 사용하는 곳이 없으므로 제거해도 됨

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
