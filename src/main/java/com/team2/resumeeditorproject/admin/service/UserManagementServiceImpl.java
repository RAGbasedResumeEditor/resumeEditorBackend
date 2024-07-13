package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.dto.request.SearchUserRequest;
import com.team2.resumeeditorproject.admin.repository.AdminUserRepository;
import com.team2.resumeeditorproject.common.util.PageUtil;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService{

    private final AdminUserRepository adminUserRepository;

    private Page<UserDTO> convertToUserDTOPage(Page<Object[]> resultPage) {
        List<UserDTO> userDTOList = resultPage.getContent().stream().map(record -> {
            User user = (User) record[0];
            Long resumeEditCount = (Long) record[1];

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
            userDTO.setInDate(user.getCreatedDate());
            userDTO.setDelDate(user.getDeletedDate());
            userDTO.setUserNo(user.getUserNo());
            userDTO.setRole(user.getRole());
            userDTO.setAge(user.getAge());
            userDTO.setResumeEditCount(resumeEditCount.intValue());

            return userDTO;
        }).collect(Collectors.toList());

        return new PageImpl<>(userDTOList, resultPage.getPageable(), resultPage.getTotalElements());
    }

    // 회원 목록 + 페이징
    @Override
    public Page<UserDTO> getUserList(int pageNo, int size) {
        // page가 0보다 작으면 재요청
        PageUtil.checkUnderZero(pageNo);

        Pageable pageable = PageRequest.of(pageNo, size);

        Page<Object[]> userPage = adminUserRepository.findUsersWithResumeEditCount(pageable);
        Page<UserDTO> userDTOPage = convertToUserDTOPage(userPage);

        // 결과가 없는 경우
        PageUtil.checkListEmpty(userDTOPage);

        // page가 totalPages보다 크면 재요청
        int lastPageNo = userDTOPage.getTotalPages() - 1;
        PageUtil.checkExcessLastPageNo(pageNo, lastPageNo);

        return userDTOPage;
    }

    // 그룹, 키워드 검색 + 페이징
    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> searchUsersByGroupAndKeyword(SearchUserRequest searchUserRequest, int size) {
        int pageNo = searchUserRequest.getPageNo();
        String group = searchUserRequest.getGroup();
        String keyword = searchUserRequest.getKeyword();

        // pageNo가 음수값이라면 재요청
        PageUtil.checkUnderZero(pageNo);

        Pageable pageable = PageRequest.of(pageNo, size);

        Page<Object[]> userPage;

        if (StringUtils.hasText(group) && StringUtils.hasText(keyword)) {
            userPage = adminUserRepository.findByGroupAndKeyword(group, keyword, pageable);
        } else {
            userPage = adminUserRepository.findUsersWithResumeEditCount(pageable);
        }
        Page<UserDTO> userDTOPage = convertToUserDTOPage(userPage);

        PageUtil.checkListEmpty(userDTOPage);

        int lastPageNo = userDTOPage.getTotalPages() - 1;
        PageUtil.checkExcessLastPageNo(pageNo, lastPageNo);

        return userDTOPage;
    }

    // 회원탈퇴 (del_date 필드에 날짜 추가)
    @Override
    public void updateUserDeleteDate(Long userNo) {
        User user = adminUserRepository.findById(userNo)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userNo));
        user.setDeletedDate(new Date()); // 현재 시간으로 탈퇴 날짜 업데이트
        adminUserRepository.save(user);
    }

    @Override
    public void updateDelDateForRoleBlacklist() {
        // 현재 날짜 가져오기
        Date currentDate = new Date();
        // 60일 전 날짜 계산
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, -60);
        Date dateBefore60Days = calendar.getTime();

        // "ROLE_BLACKLIST"인 사용자의 정보를 가져옴
        List<User> blacklistedUsers = adminUserRepository.findByRole("ROLE_BLACKLIST");

        // 가져온 사용자 중 del_date가 60일 이상 지난 사용자의 del_date를 null로 업데이트
        for (User user : blacklistedUsers) {
            if (user.getDeletedDate() != null && user.getDeletedDate().before(dateBefore60Days)) {
                user.setDeletedDate(null);
            }
        }

        adminUserRepository.saveAll(blacklistedUsers);
    }
}
