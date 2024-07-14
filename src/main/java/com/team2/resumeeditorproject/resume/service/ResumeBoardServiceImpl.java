package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.domain.ResumeStatistics;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import com.team2.resumeeditorproject.resume.repository.ResumeBoardRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * resumeBoardServiceImpl
 *
 * @author : 안은비
 * @fileName : ResumeBoardServiceImpl
 * @since : 04/30/24
 */
@Service
public class ResumeBoardServiceImpl implements ResumeBoardService{
    @Autowired
    private ResumeBoardRepository resumeBoardRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResumeBoardDTO insertResumeBoard(ResumeBoardDTO resumeboardDTO) {
        ResumeStatistics resumeStatistics = modelMapper.map(resumeboardDTO, ResumeStatistics.class);
        ResumeStatistics savedResumeStatistics = resumeBoardRepository.save(resumeStatistics);
        return modelMapper.map(savedResumeStatistics, ResumeBoardDTO.class);
    }


    @Override
    public Page<Object[]> getAllResumeBoards(Pageable pageable) {
        return resumeBoardRepository.findAllResumeBoards(pageable);
    }

    @Override
    public Object getResumeBoard(long resumeNo) {
        return resumeBoardRepository.findResumeBoard(resumeNo);
    }

    @Override
    public Page<Object[]> searchBoard(String keyword, Pageable pageable) {
        return resumeBoardRepository.findSearchBoard(keyword, pageable);
    }

    @Override
    public ResumeBoardDTO getResumeBoardForRating(Long resumeNo) {
        ResumeStatistics resumeStatisticsEntity = resumeBoardRepository.findByResumeNo(resumeNo);
        ResumeBoardDTO resumeBoardDTO = modelMapper.map(resumeStatisticsEntity, ResumeBoardDTO.class);
        return resumeBoardDTO;
    }

    @Override
    @Transactional
    public int updateRatingCount(Long resumeNo, int newRatingCount, float newRating) {
        return resumeBoardRepository.updateRatingCount(resumeNo, newRatingCount, newRating);
    }

    @Override
    public List<Object[]> getBoardRankingReadNum() {
        return resumeBoardRepository.getBoardRankingReadNum();
    }

    @Override
    public List<Object[]> getBoardRankingRating() {
        return resumeBoardRepository.getBoardRankingRating();
    }

    @Override
    public Page<Object[]> getBookmarkList(long userNo, Pageable pageable) {
        return resumeBoardRepository.getBookmarkList(userNo, pageable);
    }



//    @Override
//    public float getRating(long resumeNo) {
//        return resumeBoardRepository.getRatingByResumeNo(resumeNo);
//    }


}
