package com.team2.resumeeditorproject.resume.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * resumeBoardDTO
 *
 * @author : 안은비
 * @fileName : ResumeBoardDTO
 * @since : 04/30/24
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeBoardDTO {
    @JsonProperty("resume_no")
    private Long resumeNo;

    private float rating;

    @JsonProperty("rating_count")
    private int ratingCount;

    @JsonProperty("read_count")
    private int readCount;

    private String title;

    //for admin
    @JsonProperty("created_date")
    private Date w_date;

    private String username;
}
