package com.team2.resumeeditorproject.admin.dto.response;

import com.team2.resumeeditorproject.review.dto.ReviewDTO;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Builder
@Getter
public class ReviewListResponse {
    private List<ReviewDTO> reviewDTOList;
    private int totalPage;
}
