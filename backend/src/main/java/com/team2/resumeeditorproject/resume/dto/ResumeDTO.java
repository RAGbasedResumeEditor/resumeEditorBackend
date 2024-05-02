package com.team2.resumeeditorproject.resume.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
/**
 * resumeDTO
 *
 * @author : 안은비
 * @fileName : ResumeDTO
 * @since : 04/25/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDTO {
    private Long r_num;
    private String content;
    private Date wdate;
    private Long u_num;
}
