package com.team2.resumeeditorproject.admin.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TrafficDTO {
    private Long t_num;
    private int visitCount;
    private int editCount;
    private LocalDate inDate;
}
