package com.team2.resumeeditorproject.common.dto.response;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonListResponse<T> {
	private List<T> response;
	private String status;
	private Date time;
	private int totalPages;

	CommonListResponse(List<T> response, String status, Date time, int totalPages) {
		this.response = response;
		this.status = status;
		this.time = ObjectUtils.defaultIfNull(time, new Date());
		this.totalPages = totalPages;
	}

}
