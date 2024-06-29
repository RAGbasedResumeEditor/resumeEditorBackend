package com.team2.resumeeditorproject.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class  Refresh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reNum;

    private String username;
    private String refresh;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiration;

}
