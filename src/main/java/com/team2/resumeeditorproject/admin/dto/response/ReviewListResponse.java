package com.team2.resumeeditorproject.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team2.resumeeditorproject.review.dto.ReviewDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ReviewListResponse {
    @JsonProperty("review_list")
    private List<ReviewDTO> reviewDTOList;
    private int totalPage;
}
