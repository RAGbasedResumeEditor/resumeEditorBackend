package com.team2.resumeeditorproject.common.dto.response;

import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;

import com.team2.resumeeditorproject.common.enumeration.ResultMessage;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResultMessageResponse {
	private ResultMessage response;
	private String status;
	private Date time;

	public ResultMessageResponse(ResultMessage response, String status, Date time) {
		this.response = response;
		this.status = status;
		this.time = ObjectUtils.defaultIfNull(time, new Date());
	}
}
