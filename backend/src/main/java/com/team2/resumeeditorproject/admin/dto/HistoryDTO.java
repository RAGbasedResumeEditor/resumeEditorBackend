package com.team2.resumeeditorproject.admin.dto;

import lombok.Data;

import java.util.Date;

@Data
public class HistoryDTO {
    private Long hNum;
    private int traffic;
    private int editCount;
    private String userMode;
    private String userStatus;
    private String userGender;
    private String userAge;
    private String userOccu;
    private String userComp;
    private String userWish;
    private String editMode;
    private String editStatus;
    private String editAge;
    private String editDate;
    private String editOccu;
    private String editComp;
    private Date wDate;
}
