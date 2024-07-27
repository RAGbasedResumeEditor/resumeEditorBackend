package com.team2.resumeeditorproject.occupation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Occupation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long occupationNo;

	@Column(length = 500)
	private String occupationName;
}
