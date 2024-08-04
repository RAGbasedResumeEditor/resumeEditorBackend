package com.team2.resumeeditorproject.resume.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * resumeBoardDTO
 *
 * @author : 안은비
 * @fileName : ResumeBoardDTO
 * @since : 04/30/24
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResumeBoardDTO {
	private Long resumeBoardNo;
	private Long resumeNo;
	private float rating;
	private int ratingCount;
	private int readCount;
	private String title;
	private String content;
	private Date createdDate;
	private String username;
	private Long userNo;
	private String questions;
}
