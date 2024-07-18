package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.exception.NotFoundException;
import com.team2.resumeeditorproject.resume.domain.Resume;
import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import com.team2.resumeeditorproject.resume.dto.ResumeDTO;
import com.team2.resumeeditorproject.resume.repository.ResumeEditRepository;
import com.team2.resumeeditorproject.resume.repository.ResumeRepository;
import com.team2.resumeeditorproject.user.dto.ResumeEditDetailDTO;
import com.team2.resumeeditorproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
/**
 * resumeServiceImpl
 *
 * @author : 안은비
 * @fileName : ResumeServiceImpl
 * @since : 04/29/24
 */
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService{
    private final ResumeRepository resumeRepository;
    private final ResumeEditRepository resumeEditRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Override
    public ResumeDTO insertResume(ResumeDTO resumeDTO) {
        Resume resume = modelMapper.map(resumeDTO, Resume.class);
        Resume savedResume = resumeRepository.save(resume);
        return modelMapper.map(savedResume, ResumeDTO.class);
    }

    @Override
    public String getResumeContent(long resumeNo) {
        // resumeNo을 사용하여 resume 테이블에서 해당 레코드를 조회
        Optional<Resume> resumeOptional = resumeRepository.findById(resumeNo);

        // 레코드가 존재하는지 확인하고, 존재하면 content 값을 반환
        if (resumeOptional.isPresent()) {
            Resume resume = resumeOptional.get();
            System.out.println("--------------------" + resume);

            return resume.getContent();
        } else {
            // 레코드가 존재하지 않으면 예외 처리 또는 적절한 방법으로 처리
            throw new RuntimeException("Resume not found for resumeNo: " + resumeNo);
        }
    }

    @Override
    public ResumeEditDetailDTO getResumeEditDetail(Long rNum, String username) {
        ResumeEdit resumeEdit = resumeEditRepository.findResumeEditsByRNum(rNum)
                .stream()
                .findFirst()
                .orElse(null);
        if (resumeEdit == null) {
            throw new NotFoundException(" - resume edit with r_num " + rNum + " not found");
        }

        Resume resume = resumeEdit.getResume();
        if (resume == null) { // 해당하는 첨삭 기록이 없다면
            throw new NotFoundException(" - resume with r_num " + rNum + " not found");
        }

        Long uNum = userService.showUser(username).getUserNo();
        if (!uNum.equals(resume.getUser().getUserNo())) {
            throw new BadRequestException(" - 잘못된 접근입니다. (로그인한 사용자의 첨삭 기록이 아닙니다)");
        }

        return ResumeEditDetailDTO.builder()
                .resumeEditNo(resumeEdit.getResumeEditNo())
                .companyNo(resumeEdit.getCompany().getCompanyNo())
                .companyName(resumeEdit.getCompany().getCompanyName())
                .occupationNo(resumeEdit.getOccupation().getOccupationNo())
                .occupationName(resumeEdit.getOccupation().getOccupationName())
                .questions(resumeEdit.getCompany().getQuestions())
                .options(resumeEdit.getOptions())
                .rContent(resumeEdit.getContent())
                .mode(resumeEdit.getMode())
                .content(resume.getContent())
                .createdDate(resume.getCreatedDate())
                .build();
    }

    @Override
    public Page<Object[]> myPageEditList(long userNo, Pageable pageable) {
        return resumeRepository.getMyPageEditList(userNo, pageable);
    }

}
