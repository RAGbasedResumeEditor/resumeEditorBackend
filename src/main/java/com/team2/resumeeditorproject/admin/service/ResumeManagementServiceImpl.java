package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminResumeBoardRepository;
import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeManagementServiceImpl implements ResumeManagementService{

    private final AdminResumeBoardRepository adminResumeBoardRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ResumeBoard> getResumeBoards(int pageNo) {
        Pageable pageable= PageRequest.of(pageNo, 10, Sort.by("RNum").descending());
        Page<ResumeBoard> pageResult = adminResumeBoardRepository.findAll(pageable);
        return pageResult;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResumeBoardDTO> getPagedResumeBoards(Pageable pageable) {
        Page<Object[]> resultsPage = adminResumeBoardRepository.findAllResumeBoards(pageable);

        List<ResumeBoardDTO> dtoList = resultsPage.getContent().stream()
                .map(resumeBoardData -> new ResumeBoardDTO(
                        ((ResumeBoard) resumeBoardData[0]).getRNum(),
                        (float) Math.round(((ResumeBoard) resumeBoardData[0]).getRating() * 10) / 10,
                        ((ResumeBoard) resumeBoardData[0]).getRating_count(),
                        ((ResumeBoard) resumeBoardData[0]).getRead_num(),
                        (String) resumeBoardData[1],
                        pageable.getPageNumber(),  // 현재 페이지 번호
                        pageable.getPageSize()    // 페이지 사이즈
                ))
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, resultsPage.getTotalElements());
    }

    @Override
    @Transactional
    public void deleteResume(Long rNum) {
        if (rNum == null) {
            throw new BadRequestException("존재하지 않는 자소서입니다.");
        }
        adminResumeBoardRepository.deleteById(rNum);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResumeBoard> searchByTitle (String title, int pageNo) {
        if(title == null) title = "없는 페이지";
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by("RNum").descending());
        Page<ResumeBoard> pageResult = adminResumeBoardRepository.findByTitleContaining(title, pageable);
        return pageResult;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResumeBoard> searchByRating (Float rating, int pageNo) {
        if(rating > 5) rating = 5f;
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by("RNum").descending());
        Page<ResumeBoard> pageResult = adminResumeBoardRepository.findByRatingBetween(rating, rating+0.99f, pageable);
        return pageResult;
    }

    @Override
    public Boolean checkResumeExists(Long rNum) {
        Optional<ResumeBoard> resumeBoard = adminResumeBoardRepository.findById(rNum);
        if (resumeBoard.isPresent()) {
            return true;
        }else{
            return false;
        }
    }
}
