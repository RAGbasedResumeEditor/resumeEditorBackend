package com.team2.resumeeditorproject.user.Jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.resumeeditorproject.statistics.interceptor.TrafficInterceptor;
import com.team2.resumeeditorproject.user.domain.Refresh;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.RefreshDTO;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.RefreshRepository;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final UserRepository userRepository;
    private final CustomAuthenticationFailureHandler failureHandler;
    private final TrafficInterceptor trafficInterceptor;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,
                       RefreshRepository refreshRepository, UserRepository userRepository,
                       CustomAuthenticationFailureHandler failureHandler,
                       TrafficInterceptor trafficInterceptor){
        this.authenticationManager=authenticationManager;
        this.jwtUtil=jwtUtil;
        this.refreshRepository=refreshRepository;
        this.userRepository=userRepository;
        this.failureHandler = failureHandler;
        this.trafficInterceptor = trafficInterceptor;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //클라이언트 요청에서 username, password 호출
        UserDTO userDTO = new UserDTO();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

                userDTO = objectMapper.readValue(messageBody, UserDTO.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(username, password, null);

        //token에 담은 검증을 위한 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    @Override // 인증 성공 시
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        // 로그인 시 트래픽 카운트 증가
        trafficInterceptor.incrementTrafficCount();

        //Access/Refresh 토큰 발급
        //유저 정보(username, role) 꺼내오기
        String username = authentication.getName();

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

        try(PrintWriter out = response.getWriter()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "Success");
            responseBody.put("time", new Date());
            responseBody.put("response", "Authentication successful");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(out, responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override // 인증 실패 시
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("Authentication fail");
        failureHandler.onAuthenticationFailure(request, response, exception);
    }

    // refresh토큰을 DB에 저장하여 관리하기 위한 메서드
    @Transactional
    private synchronized void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshDTO refreshDTO = new RefreshDTO();
        refreshDTO.setUsername(username);
        refreshDTO.setRefresh(refresh);
        refreshDTO.setExpiration(date);

        // Refresh 엔티티로 변환
        Refresh refreshEntity = new Refresh();
        refreshEntity.setUsername(refreshDTO.getUsername());
        refreshEntity.setRefresh(refreshDTO.getRefresh());
        refreshEntity.setExpiration(refreshDTO.getExpiration());

        refreshRepository.save(refreshEntity);
        //=>토큰을 생성하고 난 이후에 값 저장
    }
}
