package com.team2.resumeeditorproject.admin.service;

import java.util.Map;

//통계 관련 service
public interface AdminService {

    Map<String, Object> userCnt();
    Map<String, Object> genderCnt();
    Map<String, Object> occupCnt(String occupation);
    Map<String, Object> wishCnt(String wish);
    Map<String, Integer> ageCnt();
    Map<String, Object> statusCnt();
    Map<String, Object> modeCnt();

    Map<String, Object> CompResumeCnt(String company);
    Map<String, Object> OccupResumeCnt(String occupation);

    Map<String, Long> resumeEditCntByStatus();
}
