package com.team2.resumeeditorproject.admin.dto.response;

import com.team2.resumeeditorproject.admin.dto.LandingPageReviewDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class LandingPageReviewsResponse {
    List<LandingPageReviewDTO> reviews;
}
