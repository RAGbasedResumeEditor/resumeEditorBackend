package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.repository.AdminResumeBoardRepository;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeManagementServiceImpl implements ResumeManagementService{

    private final AdminResumeBoardRepository adResBoardRepository;

    @Override
    public List<ResumeBoard> getAllResume(){
        return adResBoardRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteResume(ResumeBoardDTO rbDto){
        if(rbDto.getRNum()==null){
            return;
        }
        adResBoardRepository.deleteById(rbDto.getRNum());
    }

    @Override
    @Transactional
    public void updateResume(ResumeBoardDTO rbDto) {
        ResumeBoard rb=adResBoardRepository.findById(rbDto.getRNum()).orElseThrow(()->{
            return new IllegalArgumentException("해당 자소서가 존재하지 않습니다.");
        });
        rb.setTitle(rbDto.getTitle());
        rb.setRating(rbDto.getRating());
        adResBoardRepository.save(rb);
    }

    @Override
    public List<ResumeBoard> searchByTitle(String title, int page, int size){
        if(title==null) title="없는 페이지";

        PageRequest pageRequest=PageRequest.of(page-1, size, Sort.by("RNum").descending());
        return adResBoardRepository.findByTitleContaining(title, pageRequest);
    }

    @Override
    public List<ResumeBoard> searchByRating(Float rating, int page, int size){
        if(rating==0) rating=100f;

        PageRequest pageRequest=PageRequest.of(page-1, size, Sort.by("RNum").descending());
        return adResBoardRepository.findByRatingBetween(rating, rating+0.99f);
    }
}
