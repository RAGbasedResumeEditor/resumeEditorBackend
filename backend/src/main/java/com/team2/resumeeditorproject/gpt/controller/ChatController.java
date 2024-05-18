package com.team2.resumeeditorproject.gpt.controller;

import com.team2.resumeeditorproject.gpt.dto.ChatRequest;
import com.team2.resumeeditorproject.gpt.dto.ChatResponse;
import com.team2.resumeeditorproject.gpt.service.ChatConstants;
import com.team2.resumeeditorproject.gpt.service.ChatService;
import com.team2.resumeeditorproject.gpt.service.DataIngestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author : 김상휘
 * @fileName : ChatController
 * @since : 05/08/24
 */

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    ChatService chatService;

    @Autowired
    DataIngestionService dataIngestionService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ChatResponse processMsg(@RequestBody ChatRequest chatRequest) {
        System.out.println(chatRequest.getUserMsg());
        System.out.println(ChatConstants.QDRANT_GRPC_HOST);
        System.out.println(ChatConstants.QDRANT_GRPC_PORT);
        System.out.println(ChatConstants.QDRANT_API_KEY);
        var aiMessage = chatService.rag(chatRequest);
        var response = ChatResponse.builder().aiMsg(aiMessage).build();
        return response;
    }

    @GetMapping
    public String welcome() {
        return "Welcome to Chat Bot";
    }

    @PostMapping("/setup")
    public void processMsg() {
        dataIngestionService.setupRagChatbot();
    }
}