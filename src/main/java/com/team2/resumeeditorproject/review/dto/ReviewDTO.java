package com.team2.resumeeditorproject.review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    @JsonProperty("review_no")
    private Long reviewNo;

    @JsonProperty("user_no")
    private Long userNo;

    private String content;
    private int rating;
    private int mode;
    private String display;

    @JsonProperty("created_date")
    private Date createdDate;
}
