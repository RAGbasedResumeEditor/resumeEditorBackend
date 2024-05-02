package com.team2.resumeeditorproject.gpt.service;

import com.team2.resumeeditorproject.gpt.dto.ChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatService {

    @Autowired
    BasicRagService basicRagService;

    @Autowired
    AdvancedRagService advancedRagService;

    public String rag(ChatRequest chatRequest) {
        return advancedRagService.generateAnswer(chatRequest);
    }
}