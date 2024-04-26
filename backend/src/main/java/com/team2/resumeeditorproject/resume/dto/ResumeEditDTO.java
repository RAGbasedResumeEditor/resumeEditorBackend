package com.team2.resumeeditorproject.resume.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeEditDTO {
    private Long rNum;
    private String company;
    private String occupation;
    private JsonNode items;
    private JsonNode awards;
    private JsonNode experience;
    private String options;
    private String r_content;
    private int mode;
}
