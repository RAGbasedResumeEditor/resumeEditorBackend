package com.team2.resumeeditorproject.admin.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@Entity
@Table(name="history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class History {
    @Id
    @Column(nullable = false, name = "h_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
