package com.team2.resumeeditorproject.sample2.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Board {
    @Id @GeneratedValue
    private Long id;
    private String age;
    private String name;
    public Board() {
    }
    public Board(String name, String age) {
        this.name = name;
        this.age = age;
    }
}