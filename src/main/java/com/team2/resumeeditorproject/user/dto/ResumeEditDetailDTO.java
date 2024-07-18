package com.team2.resumeeditorproject.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("beforeContent")
    private String rContent;

    private int mode;

    @JsonProperty("afterContent")
    private String content;

    private Date createdDate;
}
