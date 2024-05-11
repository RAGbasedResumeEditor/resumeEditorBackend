package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;

import java.util.List;

public interface AdminService {
    List<ResumeBoard> getAllResume();
    void userCnt();
    void resumeList();
}
