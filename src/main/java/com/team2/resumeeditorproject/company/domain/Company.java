
package com.team2.resumeeditorproject.company.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long companyNo;

	@Column(length = 500, unique = true)
	private String companyName;

	@Column(length = 5000)
	private String questions;

}
