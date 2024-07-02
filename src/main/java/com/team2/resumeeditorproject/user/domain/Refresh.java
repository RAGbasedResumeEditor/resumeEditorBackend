package com.team2.resumeeditorproject.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
