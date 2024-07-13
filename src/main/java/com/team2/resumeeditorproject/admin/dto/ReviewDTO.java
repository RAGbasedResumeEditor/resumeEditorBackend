package com.team2.resumeeditorproject.admin.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDTO {
	private Long reviewNo;
	private Long userNo;
	private String content;
	private int rating;
	private int mode;
	private Date registerDate;
	private String display;
}
