package com.team2.resumeeditorproject.statistics.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class DailyStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dailyStatisticsNo;

    private int visitCount;
    private int editCount;

    @Column(nullable = false)
    private LocalDate referenceDate;
}
