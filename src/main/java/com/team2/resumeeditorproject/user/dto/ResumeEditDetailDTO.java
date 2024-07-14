package com.team2.resumeeditorproject.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ResumeEditDetailDTO {
    @JsonProperty("r_num")
    private Long rNum;
    private String company;
    private String occupation;
    private String item;
    private String options;

    @JsonProperty("r_content")
    private String rContent;
    private int mode;
    private String content;

    @JsonProperty("w_date")
    private Date wDate;
}
