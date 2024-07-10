package com.team2.resumeeditorproject.statistics.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long h_num;

    private int traffic;
    private int edit_count;

    @Column(columnDefinition = "JSON")
    private String user_mode;

    @Column(columnDefinition = "JSON")
    private String user_status;

    @Column(columnDefinition = "JSON")
    private String user_gender;

    @Column(columnDefinition = "JSON")
    private String user_age;

    @Column(columnDefinition = "JSON")
    private String user_occu;

    @Column(columnDefinition = "JSON")
    private String user_comp;

    @Column(columnDefinition = "JSON")
    private String user_wish;

    @Column(columnDefinition = "JSON")
    private String edit_mode;

    @Column(columnDefinition = "JSON")
    private String edit_status;

    @Column(columnDefinition = "JSON")
    private String edit_age;

    @Column(columnDefinition = "JSON")
    private String edit_occu;

    @Column(columnDefinition = "JSON")
    private String edit_comp;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "timestamp default current_timestamp")
    private Date w_date;

    @Column(name = "traffic_date", unique = true, nullable = false)
    private LocalDate traffic_date;
}
