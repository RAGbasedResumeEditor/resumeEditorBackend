package com.team2.resumeeditorproject.bookmark.domain;

import java.util.Date;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
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

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "bookmark", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"user_no", "resume_board_no"})
})
public class Bookmark {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookmarkNo;

	private Date createdDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resume_board_no", foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
	private ResumeBoard resumeBoard;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_no", foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
	private User user;
}
