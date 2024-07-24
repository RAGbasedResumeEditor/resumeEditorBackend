package com.team2.resumeeditorproject.statistics.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@ToString
public class StatisticsHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statisticsHistoryNo;

    @Column(nullable = false)
    private int visitCount;
    @Column(nullable = false)
    private int editCount;
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String userMode;
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String userStatus;
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String userGender;
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String userAge;
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String userOccupation;
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String userCompany;
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String userWish;
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String editMode;
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String editStatus;
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String editAge;
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String editOccupation;
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String editCompany;
    @Column(nullable = false)
    @CreationTimestamp
    private Date createdDate;
    @Column(nullable = false)
    private Date referenceDate;
}
