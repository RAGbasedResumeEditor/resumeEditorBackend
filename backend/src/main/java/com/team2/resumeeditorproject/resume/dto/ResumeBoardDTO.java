package com.team2.resumeeditorproject.resume.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long r_num;
    private float rating;
    private int read_num;
    private String title;
}