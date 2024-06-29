package com.team2.resumeeditorproject.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.resumeeditorproject.user.Jwt.JWTUtil;
import com.team2.resumeeditorproject.user.domain.Refresh;
import com.team2.resumeeditorproject.user.repository.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private final RefreshRepository refreshRepository;

    public ReissueController(JWTUtil jwtUtil, RefreshRepository refreshRepository) {

        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        // 헤더에서 refresh키에 담긴 토큰을 꺼냄
        String refresh = request.getHeader("refresh");

        //  refresh키값이 없다면
        if (refresh == null) {

            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST); //특정한 상태코드 응답
        }

        // 있다면 만료 확인
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {

            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        } //=> 토큰 검증 완료

        //토큰 검증 후 DB에 refresh토큰이 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {

            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        Long uNum = jwtUtil.getUNum(refresh);
        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);
        int mode = jwtUtil.getMode(refresh);

        // 새로운 access토큰 발급
        String newAccess = jwtUtil.createJwt(uNum, mode, "access", username, role, 3600000L); //생명주기 1시간
        // refresh토큰 만료 후 refresh토큰 갱신
        String newRefresh = jwtUtil.createJwt(uNum, mode, "refresh", username, role, 1209600000L); //생명주기 2주

        // refresh토큰 저장 DB에 기존의 refresh토큰 삭제 후 새 Refresh 토큰 저장
        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(username, newRefresh, 1209600000L); //생명주기 2주

        //response
        response.setHeader("access", newAccess);
        response.setHeader("refresh", newRefresh);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "Success");
        responseBody.put("time", new Date());
        responseBody.put("response", "New tokens issued successfully");

        //System.out.println("new access token success");
        return new ResponseEntity<>(responseBody, HttpStatus.OK); //응답코드 200
    }

    // refresh토큰을 DB에 저장하여 관리하기 위한 메서드
    @Transactional
    private synchronized void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        Refresh refreshEntity = new Refresh();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date);

        refreshRepository.save(refreshEntity);
        //=>토큰을 생성하고 난 이후에 값 저장
    }

}
