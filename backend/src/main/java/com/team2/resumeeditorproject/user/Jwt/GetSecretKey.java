package com.team2.resumeeditorproject.user.jwt;

import java.security.SecureRandom;

/**
 * Jwt Token을 사용하기 위한 secret key 생성
 *
 * @author : 신아진
 * @fileName : GetSecretKey
 * @since : 04/25/24
 */
public class GetSecretKey {
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32];
        random.nextBytes(keyBytes);

        //바이트 배열을 hex문자열로 변환하여 출력
        StringBuilder sb = new StringBuilder();
        for(byte b : keyBytes){
            sb.append(String.format("%02x", b));
        }
        String secretKeyHex = sb.toString();
        //System.out.println("Generate Secret Key : " + secretKeyHex);
    }
}
