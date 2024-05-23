package com.team2.resumeeditorproject.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDTO {
    private long hNum;
    private int traffic;
    private int edit_count;
    private int pro_user;
    private String occu_count;
    private String age_count;
    private String gender_count;
    private String comp_count;
    private String wish_count;
    private String status_count;
    private Date w_date;
}
