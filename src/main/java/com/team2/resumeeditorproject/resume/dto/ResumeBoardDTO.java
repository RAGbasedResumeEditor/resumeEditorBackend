package com.team2.resumeeditorproject.resume.dto;

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
    private Long RNum;
    private float rating;
    private int rating_count;
    private int read_num;
    private String title;

    //for admin
    private Date w_date;
    private String username;
}
