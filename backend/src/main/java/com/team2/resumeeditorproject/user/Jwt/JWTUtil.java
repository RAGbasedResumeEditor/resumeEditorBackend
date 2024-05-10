package com.team2.resumeeditorproject.user.Jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil { // ver 0.12.3
    private SecretKey secretKey;
    public JWTUtil(@Value("${spring.jwt.secret}") String secret){
        secretKey=new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }//JWTUtill()

    public String getUsername(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }
    public String getRole(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }
    public Boolean isExpired(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
                .getExpiration().before(new Date());
    }
    //카테고리 판별
    public String getCategory(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    public Long getUNum(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("uNum", Long.class);
    }
    // 토큰을 생성할 메서드
    public String createJwt(Long uNum, String category,String username, String role, Long expiredMs){

        return Jwts.builder()
                //claim : JWT에 저장되는 정보
                .claim("category", category) //access, refresh 판단하여 refresh토큰을 가지고 access에 접근하면 사용할 수 없도록 설정
                .claim("username", username)
                .claim("role", role)
                .claim("uNum", uNum) //uNum 추가
                // 토큰 발행시간
                .issuedAt(new Date(System.currentTimeMillis()))
                // 토큰 소멸시간(만료시간)
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                // 최종 암호화
                .signWith(secretKey)
                // 토큰 생성
                .compact();
    }
}
