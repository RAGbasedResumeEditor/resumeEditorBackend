package com.team2.resumeeditorproject.review.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
	private Long reviewNo;
	private Long userNo;
	private String username;
	private String content;
	private int rating;
	private int mode;
	private String display;
	private Date createdDate;

}
