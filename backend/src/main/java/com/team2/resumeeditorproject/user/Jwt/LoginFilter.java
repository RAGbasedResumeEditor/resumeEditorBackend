package com.team2.resumeeditorproject.user.Jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.resumeeditorproject.user.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil){
        this.authenticationManager=authenticationManager;
        this.jwtUtil=jwtUtil;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //클라이언트 요청에서 username, password 호출
        String username=obtainUsername(request);
        String password=obtainPassword(request);

        System.out.println("username: "+username);
        System.out.println("password: "+password);

        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authToken);
    }
    @Override // 인증 성공 시
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        //Access/Refresh 토큰 발급-----------------------------------
        //유저 정보(username, role) 꺼내오기
        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //토큰 생성(JWTUtil 에서 발급한 응답 값 호출)
        String access = jwtUtil.createJwt("access", username, role, 3600000L); //생명주기 1시간
        String refresh = jwtUtil.createJwt("refresh", username, role, 86400000L); //생명주기 24시간

        //응답 설정
        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value()); //200

        try(PrintWriter out = response.getWriter()){
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("time", String.valueOf(new Date()));
            responseBody.put("response", "Authentication successful");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(out, responseBody);
        }catch(IOException e){
             e.printStackTrace();
        }
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60); //생명주기
        //cookie.setSecure(true); //HttpS?통신을 진행할 경우 이 값을 넣어줌
        //cookie.setPath("/");
        cookie.setHttpOnly(true); //클라이언트단에서 자바스크립트로 해당 쿠키가 접근하지 못하도록 필수적으로 막아 두어야함

        return cookie;
    }


    @Override // 인증 실패 시
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("Login Fail");
        response.setStatus(401); // 로그인 실패 시 401 응답 코드 반환
    }
}
