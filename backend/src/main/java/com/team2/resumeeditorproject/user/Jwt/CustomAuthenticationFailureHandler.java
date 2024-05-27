package com.team2.resumeeditorproject.user.Jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.resumeeditorproject.user.Error.UserBlacklistedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "https://resume-editor-frontend-indol.vercel.app");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Max-Age", "3600");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "Fail");
        responseBody.put("time", new Date());

        Throwable cause = exception.getCause();

        // UsernameNotFoundException을 직접 처리
        if (cause instanceof UsernameNotFoundException) {
            responseBody.put("response", "User not found: " + exception.getMessage());
        }
        // 블랙리스트에 있는 경우
        else if (exception instanceof UserBlacklistedException) {
            responseBody.put("response", exception.getMessage());
        }else if (exception instanceof BadCredentialsException) {
            responseBody.put("response", "Authentication failed: " + exception.getMessage());
        }
        // 기타 인증 관련 예외 처리
        else {
            responseBody.put("response", "Authentication failed: " + exception.getMessage());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}
