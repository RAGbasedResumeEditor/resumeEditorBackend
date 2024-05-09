package com.team2.resumeeditorproject.user.controller;

import com.team2.resumeeditorproject.user.Jwt.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * Refresh 토큰을 받아 새로운 Access 토큰을 응답
 *
 * @author : 신아진
 * @fileName : ReissueController
 * @since : 05/02/24
 */
@Controller
@ResponseBody
public class ReissueController {
    private final JWTUtil jwtUtil;

    public ReissueController(JWTUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        //service단에서 구현하는것을 추천

        //get refresh token
        // 헤더에서 refresh키에 담긴 토큰을 꺼냄
        String refresh = request.getHeader("refresh");

        //쿠키에 refresh키값이 없다면
        if (refresh == null) {

            //response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST); //특정한 상태코드 응답
        }

        // 있다면 만료 확인
        //expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            //response status code
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {

            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        } //=> 토큰 검증 완료

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        // 새로운 access토큰 발급
        String newAccess = jwtUtil.createJwt("access", username, role, 3600000L); //생명주기 1시간

        //response
        response.setHeader("access", newAccess);

        //System.out.println("new access token success");
        return new ResponseEntity<>(HttpStatus.OK); //응답코드 200
    }
}
