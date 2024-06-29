package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.repository.AdminResumeEditRepository;
// TODO : 미사용 import는 제거
import com.team2.resumeeditorproject.admin.repository.AdminUserRepository;
import com.team2.resumeeditorproject.admin.service.UserManagementService;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.RefreshRepository;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import com.team2.resumeeditorproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// TODO : import할 때 와일드카드는 사용하지 않는 것이 좋음
import java.util.*;

@Controller
@RequiredArgsConstructor
// TODO : url이 /admin/user/list 어드민의 유저의 리스트라고 해석되면 좀 어색한데 /management/user/list 가 차라리 낫다고 생각함 admin은 권한에 해당하는 명칭으로 보이는데 rest api의 url에 포함되는건 어색함
// TODO : 화면과 api의 url을 꼭 동일하게 가져갈 필요도 없어서 /admin 페이지에서 /mangement/user/list를 조회하는게 맞아보임
@RequestMapping("/admin/user")
public class UserManagementController {

    private final UserManagementService userManagementService;

    // TODO : 단순한 조회더라도 controller에서 repository에 접근하는 것은 맞지 않음 mvc구조에 맞게 코드를 작성하는 것이 중요
    private final AdminResumeEditRepository adminResumeEditRepository;
    private final RefreshRepository refreshRepository;
    private final UserRepository userRepository;

    // TODO : 굳이 response를 만드는 메서드를 묶을 필요가 없어보임 Map<String, Object>를 DTO로 변경하고 userDTOList를 Service로 옮기고나면 아래처럼 한줄로 정리됨
    // TODO : return ResponseEntitiy.ok().body(new ListResponse(list, page));
    private ResponseEntity<Map<String, Object>> createResponse(Page<User> userList){
        List<UserDTO> userDTOList = new ArrayList<>();

        for(User user : userList){
            long uNum = user.getUNum();
            // TODO : controller에서 repository에서 직접데이터를 가져오는 것은 맞지 않고, service로 한번 감싸서 List<UserDTO>를 만들어서 가져오는 것이 맞아보임
            // TODO : controller에서 해야할일, service에서 해야할 일, repository에서 해야할 일을 명확하게 구분하고 목적에 맞는 클래스에서 코드를 작성하는 것이 중요
            int resumeEditCount = adminResumeEditRepository.countByRNum(uNum);

            // TODO : Builder 패턴으로 변경하는 것이 좋아보이고, Builder 패턴을 모른다면 알아보고 적용해보는 것이 좋음
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(user.getUsername());
            userDTO.setEmail(user.getEmail());
            userDTO.setGender(user.getGender());
            userDTO.setBirthDate(user.getBirthDate());
            userDTO.setCompany(user.getCompany());
            userDTO.setOccupation(user.getOccupation());
            userDTO.setWish(user.getWish());
            userDTO.setStatus(user.getStatus());
            userDTO.setMode(user.getMode());
            userDTO.setInDate(user.getInDate());
            userDTO.setDelDate(user.getDelDate());
            userDTO.setUNum(user.getUNum());
            userDTO.setRole(user.getRole());
            userDTO.setAge(user.getAge());

            // 유저 첨삭횟수
            userDTO.setResumeEditCount(resumeEditCount);

            userDTOList.add(userDTO);
        }

        // 전체 페이지 수
        int totalPages = userList.getTotalPages();

        Map<String, Object> response = new HashMap<>();
        response.put("response", userDTOList);
        response.put("totalPages",totalPages);

        return ResponseEntity.ok().body(response);
    }

    /* 회원 목록 */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getUserList(
            @RequestParam(defaultValue = "0", name = "page") int page) {

        //String role = "ROLE_USER";
        int size = 20;

        // 음수값이라면 0 페이지로 이동
        if(page<0){
            page=0;
        }

        Pageable pageable = PageRequest.of(page, size);

        // TODO : paged와 all은 상반되는 용어여서 한줄에 들어오는 것이 어색함 get paged users가 되는것이 맞아보임
        Page<User> userList = userManagementService.getAllUsersPaged(pageable);

        // TODO : 이전 클래스에서도 언급했는데 page가 totalPages보다 높다면 그에 대한 메시지를 전달하고 재요청을 유도하는 것이 맞아보임
        // TODO : API가 요청받은대로 데이터를 전달하지않고 보정을 해서 전달하는 것은 rest api rule에 맞지 않음
        // page 가 totalPages 보다 높다면 마지막 페이지로 이동
        if(page >= userList.getTotalPages()){
            page=userList.getTotalPages() - 1;
            pageable = PageRequest.of(page, size);
            userList = userManagementService.getAllUsersPaged(pageable);
        }

        return createResponse(userList);
    }

    // TODO : parameter는 DTO로 받는 것이 좋음
    /* 회원 검색 */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchUsers(
            @RequestParam(required = false, name = "group") String group,
            @RequestParam(required = false, name = "keyword") String keyword,
            @RequestParam(defaultValue = "0", name = "page") int page) {

        // TODO : 불필요한 주석은 제거, size는 상수화, 명확한 명칭 필요
        //String role = "ROLE_USER";
        int size = 20;

        // 음수값이라면 0 페이지로 이동
        if(page<0){
            page=0;
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<User> userList;

        // TODO : group, keyword가 빈문자열이면 어떻게되는지? 별개로 apache common utils에 대해서 알아보면 좋음
        // TODO : searchUsersByGroupAndKeyword로 통일하고 group, keyword에 대한 검증은 비즈니스적인 로직이어서 searchUsersByGroupAndKeyword 내부에서 하는 것이 좋아보임
        if(group !=null && keyword !=null){
            userList = userManagementService.searchUsersByGroupAndKeyword(group, keyword, pageable);
        }else{
             userList = userManagementService.getAllUsersPaged(pageable);
        }

        // TODO : 위에서 언급한것과 동일
        // page 가 totalPages 보다 높다면 마지막 페이지로 이동
        if (page >= userList.getTotalPages()) {
            page = userList.getTotalPages() - 1;
            pageable = PageRequest.of(page, size);
            if (group != null && keyword != null) {
                userList = userManagementService.searchUsersByGroupAndKeyword(group, keyword, pageable);
            } else {
                userList = userManagementService.getAllUsersPaged(pageable);
            }
        }
        return createResponse(userList);
    }

    // TODO : delete같은 행위는 url에 작성하지 않고 , request method로 설정하는 것이 좋음
    /* 회원 탈퇴 */
    @PostMapping("/delete/{uNum}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable("uNum") long uNum){
        Map<String, Object> response = new HashMap<>();
        try {
            // TODO : user탈퇴 일련의 과정을 service단으로 이동해야 함
            // uNum으로 사용자 조회
            User user = userRepository.findById(uNum)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + uNum));

            // 사용자가 이미 삭제된 상태인지 확인
            if (user.getDelDate() != null) {
                throw new RuntimeException("User already deleted with id: " + uNum);
            }

            // 회원 탈퇴 처리 후 DB에 탈퇴 날짜 업데이트
            userManagementService.updateUserDeleteDate(uNum);

            // 해당 사용자의 refresh 토큰 정보 삭제
            refreshRepository.deleteRefreshByUsername(user.getUsername());

            // 회원의 role을 ROLE_BLACKLIST 로 변경
            user.setRole("ROLE_BLACKLIST");
            userRepository.save(user);

            response.put("response", "User deleted successfully"); // TODO : 메시지는 통함해서 enum으로 관리하는 것이 좋음 메시지 형식이 컨트롤러마다 다름
            response.put("time", new Date());
            response.put("status", "Success"); // TODO : status도 마찬가지로 enum으로 관리하면 좋음
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("response", e.getMessage()); // TODO : RuntimeException을 통으로 잡아서 그에대한 메시지를 넘기는것은 좋지 않음, e같은 단문자 사용은 지양
            response.put("time", new Date());
            response.put("status", "Fail");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("response", "Failed to delete user");
            response.put("time", new Date());
            response.put("status", "Fail");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
