
package com.team2.resumeeditorproject.resume.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Company {
	@Id
	private String companyNo;

	@Column(unique = true)
	private String companyName;

	private String questions;
}
