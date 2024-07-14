package com.team2.resumeeditorproject.user.dto;

import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import lombok.Data;

import java.util.Date;

@Data
public class ResumeEditDetailDTO {
    private ResumeEdit resumeEdit;
    private String content;
    private Date wDate;
}
