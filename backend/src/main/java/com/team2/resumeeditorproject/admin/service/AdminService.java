package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;

//통계 관련 service
public interface AdminService {

    int userCnt();
    List<String> genderCnt();
    String occupCnt();
    void wishCnt();
    Map<String, Integer> ageCnt();
    Map<String, String> statusCnt();
    Map<String, String> modeCnt();

    void CompResumeCnt();
    void OccupResumeCnt();
}
