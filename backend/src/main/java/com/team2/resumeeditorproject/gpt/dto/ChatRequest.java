package com.team2.resumeeditorproject.gpt.dto;

import lombok.Data;

@Data
public class ChatRequest {
    private String userMsg;
    private boolean newChatThread;
}