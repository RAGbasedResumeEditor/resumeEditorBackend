package com.team2.resumeeditorproject.resume.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * resumeEditDTO
 *
 * @author : 안은비
 * @fileName : ResumeEditDTO
 * @since : 04/25/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeEditDTO {
    private long resumeEditNo;
    private long resumeNo;
    private long companyNo;
    private String companyName;
    private long occupationNo;
    private String occupationName;
    private String item;
    private String options;
    private String content;
    private Date createdDate;
    private int mode;
    private long userNo;
}
