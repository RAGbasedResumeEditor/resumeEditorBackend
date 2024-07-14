package com.team2.resumeeditorproject.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AutoCodeResponse {
    private String status;  // "Success" 또는 "Fail"
    private String message; // 상태 메시지
    private String email;   // 이메일 주소 (인증 코드 전송 성공 시 반환)
    private String delDate; // 삭제일 (30일 이내인 경우 반환, yyyy-MM-dd 형식)
    private String availableDate; // 사용 가능일 (30일 후, yyyy-MM-dd 형식)
}
