package com.team2.resumeeditorproject.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ResumeEditDetailDTO {
    @JsonProperty("resume_edit_no")
    private Long resumeEditNo;

    @JsonProperty("company_no")
    private Long companyNo;

    @JsonProperty("company")
    private String companyName;

    @JsonProperty("occupation_no")
    private Long occupationNo;

    @JsonProperty("occupation")
    private String occupationName;

    private String questions;
    private String options;

    @JsonProperty("before_content")
    private String rContent;

    private int mode;

    @JsonProperty("after_content")
    private String content;

    @JsonProperty("created_date")
    private Date createdDate;
}
