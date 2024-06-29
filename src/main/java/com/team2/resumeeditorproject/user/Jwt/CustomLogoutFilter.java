package com.team2.resumeeditorproject.user.Jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.resumeeditorproject.user.repository.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CustomLogoutFilter extends GenericFilterBean {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public CustomLogoutFilter(JWTUtil jwtUtil, RefreshRepository refreshRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }
    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 모든 요청은 이 필터를 통해 거쳐간다. 로그아웃 요청만 획득해야한다.

        // 로그아웃인지 아닌지 확인하기
        // 로그아웃 경로가 아니면 다음 필터로 넘어감
        String requestUri = request.getRequestURI();
        if (!requestUri.matches("^\\/logout$")) {
            filterChain.doFilter(request, response);
            return;
        }
        // 로그아웃이지만 POST요청이 아니면 다음 필터로 넘어감
        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {

            filterChain.doFilter(request, response);
            return;
        }
        // => POST경로로 로그아웃이 온 것을 확인

        // 헤더에 refresh토큰이 있는지 확인
        String refresh = request.getHeader("refresh");

        // 헤더에 refresh토큰이 없을 경우
        if (refresh == null) {

            response.getWriter().write("Refresh token does not exist");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //400응답
            return;
        }

        // 토큰 만료여부 확인
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            //만료가 되었다면 이미 로그아웃된 상태이기 때문에 추가적으로 로그아웃 작업을 진행하지 않음
            response.getWriter().write("You have already been logged out.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 해당 토큰이 활성화 되어있다면
        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {

            response.getWriter().write("This is not a refresh token.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 토큰이 refresh 라면
        // DB에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {

            response.getWriter().write("You have already been logged out.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        // => 토큰 검증 완료

        // 로그아웃 진행
        // Refresh 토큰 DB에서 제거
        refreshRepository.deleteByRefresh(refresh);

        response.setHeader("refresh",null);
        response.setStatus(HttpServletResponse.SC_OK);

        try(PrintWriter out = response.getWriter()){
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "Success");
            responseBody.put("time", new Date());
            responseBody.put("response", "You have been successfully logged out.");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(out, responseBody);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
