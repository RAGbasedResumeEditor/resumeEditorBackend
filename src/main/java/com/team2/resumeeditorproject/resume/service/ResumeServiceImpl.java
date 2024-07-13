package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.domain.Resume;
import com.team2.resumeeditorproject.resume.dto.ResumeDTO;
import com.team2.resumeeditorproject.resume.repository.ResumeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ResumeServiceImpl implements ResumeService{
    @Autowired
    private ResumeRepository resumeRepository;
    @Autowired
    private ModelMapper modelMapper;

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
    public Object getResumeEditDetail(Long num) {
        return resumeRepository.getResumeEditDetail(num);
    }

    @Override
    public Page<Object[]> myPageEditList(long userNo, Pageable pageable) {
        return resumeRepository.getMyPageEditList(userNo, pageable);
    }

}
