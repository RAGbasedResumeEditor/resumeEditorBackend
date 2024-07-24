package com.team2.resumeeditorproject.review.domain;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.team2.resumeeditorproject.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewNo;

	@Column(length = 200, nullable = false)
	private String content;
	private int rating;
	private int mode;
	@Column(length = 20, nullable = false)
	@Setter
	private String display;

	@Column(nullable = false)
	@CreationTimestamp
	private Date createdDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_no", foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
	private User user;
}
