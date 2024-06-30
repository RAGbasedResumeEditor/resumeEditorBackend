package com.team2.resumeeditorproject.review.dto;

import jakarta.persistence.Column;
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
    private boolean show;
    private Date w_date;
}
