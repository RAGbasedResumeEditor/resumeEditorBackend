package com.team2.resumeeditorproject.admin.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class StatisticsHistoryDTO {
    private Long statisticsHistoryNo;
    private int visitCount;
    private int editCount;
    private String userMode;
    private String userStatus;
    private String userGender;
    private String userAge;
    private String userOccupation;
    private String userCompany;
    private String userWish;
    private String editMode;
    private String editStatus;
    private String editAge;
    private String editDate;
    private String editOccupation;
    private String editCompany;
    private Date registerDate;
    private LocalDate referenceDate;
}
