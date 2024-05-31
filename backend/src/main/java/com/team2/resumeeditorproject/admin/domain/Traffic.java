package com.team2.resumeeditorproject.admin.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name="traffic")
public class Traffic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long t_num;

    private int visitCount;
    private int editCount;
    private LocalDate inDate;
}
