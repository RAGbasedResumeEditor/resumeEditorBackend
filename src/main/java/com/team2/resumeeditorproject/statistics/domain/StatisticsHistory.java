package com.team2.resumeeditorproject.statistics.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@ToString
public class StatisticsHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String editOccupation;
    private String editCompany;
    private Date createdDate;
    private LocalDate referenceDate;
}
