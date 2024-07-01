package com.team2.resumeeditorproject.admin.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class HistoryDTO {
    private Long h_num;
    private int traffic;
    private int edit_count;
    private String user_mode;
    private String user_status;
    private String user_gender;
    private String user_age;
    private String user_occu;
    private String user_comp;
    private String user_wish;
    private String edit_mode;
    private String edit_status;
    private String edit_age;
    private String edit_date;
    private String edit_occu;
    private String edit_comp;
    private Date w_date;
    private LocalDate traffic_date;
}
