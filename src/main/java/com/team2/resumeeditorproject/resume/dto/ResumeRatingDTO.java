package com.team2.resumeeditorproject.resume.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResumeRatingDTO {
	private long resumeBoardNo;
	private long userNo;
	private double rating;
}
