package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;

//통계 관련 service
public interface AdminService {

    Map<String, Object> userCnt();
    Map<String, Object> genderCnt();
    Map<String, Object> occupCnt(String occupation);
    Map<String, Object> wishCnt(String wish);
    Map<String, Integer> ageCnt();
    Map<String, Object> statusCnt();
    Map<String, String> modeCnt();

    Map<String, Object> CompResumeCnt(String company);
    Map<String, Object> OccupResumeCnt(String occupation);
}
