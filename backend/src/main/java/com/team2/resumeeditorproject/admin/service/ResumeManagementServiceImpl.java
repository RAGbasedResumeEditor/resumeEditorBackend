package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminResumeBoardRepository;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeManagementServiceImpl implements ResumeManagementService{

    private final AdminResumeBoardRepository adResBoardRepository;

    @Override
    public Page<ResumeBoard> getResumeBoards(int page) {
        Pageable pageable=PageRequest.of(page-1, 10, Sort.by("RNum").descending());
        Page<ResumeBoard> pageResult=adResBoardRepository.findAll(pageable);
        return pageResult;
    }

    @Override
    @Transactional
    public void deleteResume(Long rNum){
        if(rNum==null){
            return;
        }
        adResBoardRepository.deleteById(rNum);
    }
    /*
    @Override
    @Transactional
    public void updateResume(ResumeBoardDTO rbDto) {
        ResumeBoard rb=adResBoardRepository.findById(rbDto.getRNum()).orElseThrow(()->{
            return new IllegalArgumentException("해당 자소서가 존재하지 않습니다.");
        });
        rb.setTitle(rbDto.getTitle());
        rb.setRating(rbDto.getRating());
        adResBoardRepository.save(rb);
    }*/

    @Override
    public Page<ResumeBoard> searchByTitle(String title, int page){
        if(title==null) title="없는 페이지";
        Pageable pageable=PageRequest.of(page-1, 10, Sort.by("RNum").descending());
        Page<ResumeBoard> pageResult=adResBoardRepository.findByTitleContaining(title, pageable);
        return pageResult;
    }

    @Override
    public Page<ResumeBoard> searchByRating(Float rating, int page){
        if(rating>5) rating=5f;
        Pageable pageable=PageRequest.of(page-1, 10, Sort.by("RNum").descending());
        Page<ResumeBoard> pageResult=adResBoardRepository.findByRatingBetween(rating, rating+0.99f, pageable);
        return pageResult;
    }

    @Override
    public Boolean checkResumeExists(Long rNum) {
        Optional<ResumeBoard> rb=adResBoardRepository.findById(rNum);
        if(rb.isPresent()){
            return true;
        }else{
            return false;
        }
    }
}
