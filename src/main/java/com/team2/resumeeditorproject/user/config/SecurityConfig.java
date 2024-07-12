package com.team2.resumeeditorproject.user.config;

import com.team2.resumeeditorproject.statistics.interceptor.TrafficInterceptor;
import com.team2.resumeeditorproject.user.Jwt.CustomAuthenticationFailureHandler;
import com.team2.resumeeditorproject.user.Jwt.CustomAuthenticationProvider;
import com.team2.resumeeditorproject.user.Jwt.CustomLogoutFilter;
import com.team2.resumeeditorproject.user.Jwt.JWTFilter;
import com.team2.resumeeditorproject.user.Jwt.JWTUtil;
import com.team2.resumeeditorproject.user.Jwt.LoginFilter;
import com.team2.resumeeditorproject.user.repository.RefreshRepository;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final UserRepository userRepository;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final UserDetailsService userDetailsService;
    private final TrafficInterceptor trafficInterceptor;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil,
                          RefreshRepository refreshRepository, UserRepository userRepository,
                          CustomAuthenticationFailureHandler customAuthenticationFailureHandler,
                          UserDetailsService userDetailsService, TrafficInterceptor trafficInterceptor) {

        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.userRepository = userRepository;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
        this.userDetailsService = userDetailsService;
        this.trafficInterceptor = trafficInterceptor;
    }
    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }
    //------------------------------------------------

    // 비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }

    // 세션 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //CORS 처리
        /*
         * 클라이언트가 웹브라우저를 통해서 사이트에 접속 -> 프론트서버에서 리액트로 페이지를 응답
         * -> 프론트엔드 서버는 보통 3000번대에 띄워 테스트를 하게되고 -> 응답받은 페이지에서 특정 내부 데이터를 API서버로 호출 ->
         * API데이터는 8088서버에서 응답을 보내야함 -> 이렇게 되면 프론트서버와 백엔드서버의 포트번호가 다르기 때문에 웹브라우저 단에서 CORS를 금지시켜 데이터가 보이지않게됨
         * -> 그러므로 백엔드단에서 CORS 처리를 해주어야 웹브라우저의 데이터를 볼 수 있다.
         * */
        http
                .cors((cors)->cors
                        .configurationSource(new CorsConfigurationSource() {
                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration configuration = new CorsConfiguration();
                                // 프론트에서 보낼 3000번대 포트 허용
                                configuration.setAllowedOrigins(Arrays.asList("https://reditor.me", "https://www.reditor.me","http://localhost:3000"));
                                // GET, POST 등 모든 메서드 허용
                                configuration.setAllowedMethods(Collections.singletonList("*"));
                                // 쿠키, HTTP 인증 등을 사용하는 요청을 허용
                                configuration.setAllowCredentials(true);
                                // 모든 헤더를 허용
                                configuration.setAllowedHeaders(Collections.singletonList("*"));
                                // 요청의 결과를 캐시할 수 있는 시간을 한시간 허용
                                configuration.setMaxAge(3600L);

                                //클라이언트에 노출할 헤더를 설정
                                configuration.setExposedHeaders(Arrays.asList("access","refresh"));

                                return configuration;
                            }
                        }));

        //csrf disable => 세션 방식에서는 csrf공격을 필수적으로 방어를 해주어야 하지만 JWT는 세션을 STATELESS방식으로 관리하기 때문에 csrf에 대한 공격을 방어하지 않아도 되기 때문
        http
                .csrf((auth)-> auth.disable());

        //Form 로그인 방식 disable
        http
                .formLogin((auth)-> auth.disable());

        //http basic 인증 방식 disable
        http
                .httpBasic((auth)->auth.disable());

        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth)->auth
                        // login, 루트, signup경로에 대해서는 모든 경로 허용
                        .requestMatchers("/login","/","/signup","/signup/**","/signup/exists/**","/resume-edit/**","/resume-guide/**","/landing/**","/swagger-ui/*","/v3/api-docs/**").permitAll()
                        // ADMIN권한을 가진 사용자만 접근 가능
                        .requestMatchers("/admin").hasRole("ADMIN")
                        // access토큰이 만료된 상태로 접근을 하기 때문에 로그인자체가 불가능한 상태 이므로 모든 경로 허용
                        .requestMatchers("/reissue").permitAll()
                        // 그외 요청은 로그인 사용자만 접근 가능
                        .anyRequest().authenticated());

        //필터 추가 (LoginFilter() 앞에 추가)
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
        //필터 추가 (LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요)
        http
                // LoginFilter에서 주입받은 authenticationManager를 꼭 주입해주어야 동작 가능
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository, userRepository, customAuthenticationFailureHandler, trafficInterceptor), UsernamePasswordAuthenticationFilter.class);
        //로그아웃 필터 추가
        http
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);
        // 세션 설정
        // JWT를 통한 인증/인가를 위해서 세션을 STATELESS상태로 설정하는것이 중요하다.(세션을 상태를 가지지 않는(stateless) 방식으로 처리)
        http
                .sessionManagement((session)->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        CustomAuthenticationProvider provider = new CustomAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }
}
