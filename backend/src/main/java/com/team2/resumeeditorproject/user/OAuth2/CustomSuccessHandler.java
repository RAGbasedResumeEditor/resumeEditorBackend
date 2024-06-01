package com.team2.resumeeditorproject.user.OAuth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.resumeeditorproject.admin.interceptor.TrafficInterceptor;
import com.team2.resumeeditorproject.user.Jwt.JWTUtil;
import com.team2.resumeeditorproject.user.domain.Refresh;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.repository.RefreshRepository;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshRepository refreshRepository;
    private final TrafficInterceptor trafficInterceptor;

    public CustomSuccessHandler(JWTUtil jwtUtil, UserRepository userRepository, RefreshRepository refreshRepository
    , TrafficInterceptor trafficInterceptor) {
        this.jwtUtil = jwtUtil;
        this.userRepository=userRepository;
        this.refreshRepository=refreshRepository;
        this.trafficInterceptor=trafficInterceptor;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        // 로그인 시 트래픽 카운트 증가
        trafficInterceptor.incrementTrafficCount();

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
        //로그인 username 추출
        String username = customUserDetails.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // 사용자 이름을 사용하여 사용자 정보를 조회하고 uNum, mode 가져오기
        User user = userRepository.findByUsername(username);
        Long uNum = user.getUNum();
        int mode = user.getMode();

        //토큰 생성(JWTUtil 에서 발급한 응답 값 호출)
        String access = jwtUtil.createJwt(uNum, mode,"access", username, role, 3600000L); //생명주기 1시간
        String refresh = jwtUtil.createJwt(uNum, mode, "refresh", username, role, 1209600000L); //생명주기 2주

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

        // 로그인 성공 시 redirect할 주소
        response.sendRedirect("https://www.reditor.me/main/resume");
    }

    // refresh토큰을 DB에 저장하여 관리하기 위한 메서드
    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        Refresh refreshEntity = new Refresh();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
        //=>토큰을 생성하고 난 이후에 값 저장
    }
}