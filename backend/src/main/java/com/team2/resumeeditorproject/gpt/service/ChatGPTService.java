package com.team2.resumeeditorproject.gpt.service;

import com.team2.resumeeditorproject.gpt.dto.ChatCompletionDto;
import com.team2.resumeeditorproject.gpt.dto.CompletionDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ChatGPT 서비스 인터페이스
 *
 * @author : 김상휘
 * @fileName : ChatGPTService
 * @since : 04/26/24
 */

@Service
public interface ChatGPTService {

    List<Map<String, Object>> modelList();

    Map<String, Object> isValidModel(String modelName);

    Map<String, Object> legacyPrompt(CompletionDto completionDto);

    Map<String, Object> prompt(ChatCompletionDto chatCompletionDto);
}