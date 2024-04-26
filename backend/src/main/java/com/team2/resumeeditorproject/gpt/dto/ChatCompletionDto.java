package com.team2.resumeeditorproject.gpt.dto;

import lombok.*;

import java.util.List;

/**
 * 새로운 모델에 대한 요청 객체를 관리합니다. : gpt-4, gpt-4 turbo, gpt-3.5-turbo
 *
 * @author : 김상휘
 * @fileName : ChatCompletionDto
 * @since : 4/25/24
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatCompletionDto {

    // 사용할 모델
    private String model;

    private List<ChatRequestMsgDto> messages;

    @Builder
    public ChatCompletionDto(String model, List<ChatRequestMsgDto> messages) {
        this.model = model;
        this.messages = messages;
    }
}