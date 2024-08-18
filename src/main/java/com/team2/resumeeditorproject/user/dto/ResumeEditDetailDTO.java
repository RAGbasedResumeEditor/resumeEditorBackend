package com.team2.resumeeditorproject.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ResumeEditDetailDTO {
    private Long resumeEditNo;
    private Long companyNo;
    private String companyName;
    private Long occupationNo;
    private String occupationName;
    private String questions;
    private String options;
    private String beforeContent;
    private String afterContent;
    private int mode;
    private Date createdDate;
}
