package com.team2.resumeeditorproject.user.domain;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
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
	@Column(length = 50, nullable = false)
	private String email;
	@Column(length = 50, nullable = false)
	private String code;
	@CreationTimestamp
	private Date createdDate;
	private Date expiresDate;

	public void refreshVerification(String code, Date createdDate, Date expiresDate) {
		this.code = code;
		this.createdDate = createdDate;
		this.expiresDate = expiresDate;
	}
}
