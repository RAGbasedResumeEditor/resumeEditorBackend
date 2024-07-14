package com.team2.resumeeditorproject.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long rvNum;
    private Long UNum;
    private String content;
    private int rating;
    private int mode;
    private String display;
    private Date w_date;
}
