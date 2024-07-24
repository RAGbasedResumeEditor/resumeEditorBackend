package com.team2.resumeeditorproject.resume.domain;

import com.team2.resumeeditorproject.user.domain.User;

import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resume_rating", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"user_no", "resume_board_no"})
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResumeRating {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long resumeRatingNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_no", foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resume_board_no", foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ResumeBoard resumeBoard;

	private double rating;

}
