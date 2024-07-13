package com.team2.resumeeditorproject.resume.domain;

import java.util.Date;

import com.team2.resumeeditorproject.user.domain.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class Bookmark {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookmarkNo;

	private Date createdDate;

	@ManyToOne
	@JoinColumn(name = "resume_no")
	private Resume resume;

	@ManyToOne
	@JoinColumn(name = "user_no")
	private User user;
}
