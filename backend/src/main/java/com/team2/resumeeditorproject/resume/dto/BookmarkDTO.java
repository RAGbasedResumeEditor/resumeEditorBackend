package com.team2.resumeeditorproject.resume.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDTO {
    private Long BNum;
    private Long RNum;
    private Long UNum;
}
