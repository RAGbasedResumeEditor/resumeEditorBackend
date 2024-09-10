package com.team2.resumeeditorproject.resume.domain;

import com.team2.resumeeditorproject.resume.enumulation.ResumeEditExtensionType;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResumeEditExtension {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long resumeEditExtensionNo;

	@Enumerated(EnumType.STRING)
	private ResumeEditExtensionType resumeEditExtensionType;

	@Column(length = 5000)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resume_edit_no", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ResumeEdit resumeEdit;

	public static ResumeEditExtension empty = new ResumeEditExtension();
}
