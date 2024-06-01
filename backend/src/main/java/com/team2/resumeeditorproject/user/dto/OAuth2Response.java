package com.team2.resumeeditorproject.user.dto;

public interface OAuth2Response {
    String getProvider(); // 로그인 서비스 제공자 (구글, 네이버, ...)

    String getProviderId(); // 소셜 로그인 유저가 갖는 고유한 ID

    String getEmail();

    String getName();
}
