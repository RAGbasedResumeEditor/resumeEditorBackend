package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminResumeBoardRepository;
import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.exception.NotFoundException;
import com.team2.resumeeditorproject.resume.domain.Resume;
import com.team2.resumeeditorproject.resume.domain.ResumeStatistics;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import com.team2.resumeeditorproject.user.domain.User;
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
public class ResumeManagementServiceImpl implements ResumeManagementService {

    private final AdminResumeBoardRepository adminResumeBoardRepository;

    private List<ResumeBoardDTO> convertToDTO(List<ResumeStatistics> resumeStatisticsList) {
        return resumeStatisticsList.stream()
                .map(resumeBoard -> {
                    Resume resume = resumeBoard.getResume();
                    User user = resume.getUser();
                    return new ResumeBoardDTO(
                            resumeBoard.getResumeStatisticsNo(),
                            (float) Math.round(resumeBoard.getRating() * 10) / 10,
                            resumeBoard.getRatingCount(),
                            resumeBoard.getReadCount(),
                            resumeBoard.getTitle(),
                            resume.getCreatedDate(),
                            user.getUsername()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResumeBoardDTO> getPagedResumeBoards(Pageable pageable) {
        Page<ResumeStatistics> resumeBoardPage = adminResumeBoardRepository.findAllResumeBoards(pageable);
        List<ResumeBoardDTO> dtoList = convertToDTO(resumeBoardPage.getContent());
        return new PageImpl<>(dtoList, pageable, resumeBoardPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResumeBoardDTO> searchPagedResumeBoardByTitle (String title, int pageNo) {
        if (title == null || title.trim().isEmpty()) {
            throw new BadRequestException("제목을 입력해야합니다.");
        }
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by("resumeNo").descending());
        Page<ResumeStatistics> pageResult = adminResumeBoardRepository.findByTitleContaining(title, pageable);

        if (pageResult.getContent().isEmpty()) {
            throw new NotFoundException("존재하지 않습니다.");
        }

        List<ResumeBoardDTO> dtoList = convertToDTO(pageResult.getContent());
        return new PageImpl<>(dtoList, pageable, pageResult.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResumeBoardDTO> searchPagedResumeBoardByRating (Float rating, int pageNo) {
        if (rating == null || rating < 0) {
            rating = 0f; // rating이 null 또는 음수인 경우 0으로 설정
        }
        if (rating > 5) {
            rating = 5f; // rating이 5를 초과하는 경우 5로 설정
        }
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by("resumeNo").descending());
        Page<ResumeStatistics> pageResult = adminResumeBoardRepository.findByRatingBetween(rating, rating + 0.99f, pageable);
        List<ResumeBoardDTO> dtoList = convertToDTO(pageResult.getContent());
        return new PageImpl<>(dtoList, pageable, pageResult.getTotalElements());
    }

    @Override
    @Transactional
    public void deleteResume(Long resumeNo) {
        if (resumeNo == null) {
            throw new BadRequestException("존재하지 않는 자소서입니다.");
        }
        adminResumeBoardRepository.deleteById(resumeNo);
    }

    @Override
    public Boolean checkResumeExists(Long resumeNo) {
        Optional<ResumeStatistics> resumeBoard = adminResumeBoardRepository.findById(resumeNo);
        return resumeBoard.isPresent();
    }
}
