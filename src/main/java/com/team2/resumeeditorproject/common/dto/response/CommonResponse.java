package com.team2.resumeeditorproject.common.dto.response;

import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonResponse <T> {
    private T response;
    private String status;
    private Date time;

    CommonResponse(T response, String status, Date time) {
        this.response = response;
        this.status = status;
        this.time = ObjectUtils.defaultIfNull(time, new Date());
    }
}
