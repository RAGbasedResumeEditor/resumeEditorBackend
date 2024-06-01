package com.team2.resumeeditorproject.user.OAuth2;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil2 {

    private SecretKey secretKey;

    public JWTUtil2(@Value("${spring.jwt.secret}")String secret) {
        byte[] byteSecretKey = Decoders.BASE64.decode(secret);
        secretKey = Keys.hmacShaKeyFor(byteSecretKey);
    }

    public String getUsername(String token) {

        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("username", String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    // 토큰을 생성할 메서드
    public String createJwt(Long uNum, int mode, String category,String username, String role, Long expiredMs){

        return Jwts.builder()
                //claim : JWT에 저장되는 정보
                .claim("category", category) //access, refresh 판단하여 refresh토큰을 가지고 access에 접근하면 사용할 수 없도록 설정
                .claim("username", username)
                .claim("role", role)
                .claim("uNum", uNum) //uNum 추가
                .claim("mode", mode) //mode 추가
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
