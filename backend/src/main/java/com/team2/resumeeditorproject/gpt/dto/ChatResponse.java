package com.team2.resumeeditorproject.gpt.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatResponse {
    private String aiMsg;
}