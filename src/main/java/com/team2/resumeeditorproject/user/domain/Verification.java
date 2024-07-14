package com.team2.resumeeditorproject.user.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Verification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long verificationNo;
	private String email;
	private String code;
	private Date createdDate;
	private Date expiresDate;

	public void refreshVerification(String code, Date createdDate, Date expiresDate) {
		this.code = code;
		this.createdDate = createdDate;
		this.expiresDate = expiresDate;
	}
}
