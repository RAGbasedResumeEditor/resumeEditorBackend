package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService{
    @Autowired
    private RatingRepository ratingRepository;
    @Override
    public int ratingCount(long resumeNo, long userNo) {
        return ratingRepository.countByResumeResumeNoAndUserUserNo(resumeNo, userNo);
    }
}
