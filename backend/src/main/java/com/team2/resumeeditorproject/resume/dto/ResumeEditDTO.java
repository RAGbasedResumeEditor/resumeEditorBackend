package com.team2.resumeeditorproject.resume.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * resumeEditDTO
 *
 * @author : 안은비
 * @fileName : ResumeEditDTO
 * @since : 04/25/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeEditDTO {
    private Long r_num;
    private String company;
    private String occupation;
    private JsonNode items;
    private JsonNode awards;
    private JsonNode experience;
    private String options;
    private String r_content;
    private int mode;
    private Long u_num;
}
