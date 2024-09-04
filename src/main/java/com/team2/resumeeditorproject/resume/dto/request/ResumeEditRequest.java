package com.team2.resumeeditorproject.resume.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeEditRequest {
    private long resumeEditNo;
    private long companyNo;
    private String companyName;
    private long occupationNo;
    private String occupationName;
    private String item;
    private String options;
    private String beforeContent;
    private String afterContent;
    private String question;
    private Date createdDate;
    private int mode;
    private long userNo;
}
