package com.team2.resumeeditorproject.resume.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResumeBoardRequest {
	private int pageNo;
	private int pageSize;
	private String keyword;
}
