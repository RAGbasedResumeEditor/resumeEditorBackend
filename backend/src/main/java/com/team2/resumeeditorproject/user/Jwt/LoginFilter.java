package com.team2.resumeeditorproject.user.Jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.resumeeditorproject.user.domain.Refresh;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.CustomUserDetails;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.RefreshRepository;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private RefreshRepository refreshRepository;
    private UserRepository userRepository;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, RefreshRepository refreshRepository, UserRepository userRepository){
        this.authenticationManager=authenticationManager;
        this.jwtUtil=jwtUtil;
        this.refreshRepository=refreshRepository;
        this.userRepository=userRepository;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //클라이언트 요청에서 username, password 호출

        // form-data 방식
//        String username=obtainUsername(request);
//        String password=obtainPassword(request);

        // application/json 방식
        UserDTO userDTO = new UserDTO();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

                userDTO = objectMapper.readValue(messageBody, UserDTO.class);

        }catch(IOException e){
            throw new RuntimeException(e);
        }
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

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

        // 사용자 이름을 사용하여 사용자 정보를 조회하고 uNum 가져오기
        User user = userRepository.findByUsername(username);
        Long uNum = user.getUNum();

        //토큰 생성(JWTUtil 에서 발급한 응답 값 호출)
        String access = jwtUtil.createJwt(uNum,"access", username, role, 3600000L); //생명주기 1시간
        String refresh = jwtUtil.createJwt(uNum,"refresh", username, role, 1209600000L); //생명주기 2주

        //Refresh 토큰 DB 저장
        addRefreshEntity(username, refresh, 1209600000L); //생명주기 2주

        //응답 설정
        response.setHeader("access", access);
        response.setHeader("refresh", refresh);
        response.setStatus(HttpStatus.OK.value()); //200

        try(PrintWriter out = response.getWriter()){
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "Success");
            responseBody.put("time", new Date());
            responseBody.put("response", "Authentication successful");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(out, responseBody);
        }catch(IOException e){
             e.printStackTrace();
        }
    }

    @Override // 인증 실패 시
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("Authentication fail");
        response.setStatus(401); // 로그인 실패 시 401 응답 코드 반환
    }

    // refresh토큰을 DB에 저장하여 관리하기 위한 메서드
    private void addRefreshEntity(String usenrname, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        Refresh refreshEntity = new Refresh();
        refreshEntity.setUsername(usenrname);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
        //=>토큰을 생성하고 난 이후에 값 저장
    }
}
