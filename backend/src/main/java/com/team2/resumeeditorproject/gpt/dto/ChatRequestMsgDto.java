package com.team2.resumeeditorproject.gpt.dto;
import lombok.*;

/**
 * Please explain the class!!
 *
 * @author : 김상휘
 * @fileName : ChatRequestMsgDto
 * @since : 4/25/24
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class ChatRequestMsgDto {

    private String role;

    private String content;

    @Builder
    public ChatRequestMsgDto(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
