package com.team2.resumeeditorproject.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ResumeEditDetailDTO {
    @JsonProperty("r_num")
    private Long resumeEditNo;
    private Long companyNo;
    private String companyName;
    private Long occupationNo;
    private String occupationName;
    private String questions;
    private String options;

    @JsonProperty("r_content")
    private String rContent;
    private int mode;
    private String content;

    @JsonProperty("w_date")
    private Date createdDate;
}
