package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.user.OAuth2.CustomOAuth2User;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.GoogleResponse;
import com.team2.resumeeditorproject.user.dto.NaverResponse;
import com.team2.resumeeditorproject.user.dto.OAuth2Response;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

// 소셜 로그인한 유저 정보를 받아오는 클래스
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
                                        //DefaultOAuth2UserService는 OAuth2UserService의 구현체다.
    private final UserRepository userRepository;

    @Override // 로그인한 사용자 정보를 받아오는 메서드.
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User=super.loadUser(userRequest);
        System.out.println("oAuth2User: "+oAuth2User.getAttributes());

        // 어떤 로그인 서비스(login provider)인지 구분한다. ( 구글, 네이버, 카카오 ...)
        String registrationId=userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response=null;

        if(registrationId.equals("naver")){
            oAuth2Response=new NaverResponse(oAuth2User.getAttributes());
        }else if(registrationId.equals("google")){
            oAuth2Response=new GoogleResponse(oAuth2User.getAttributes());
        }else{
            return null;
        }

        String username=oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        User existData = userRepository.findByUsername(username);

        if(existData == null){
            User user= new User();
            user.setUsername(username);
            user.setEmail(oAuth2Response.getEmail());
            user.setRole("ROLE_USER");
            userRepository.save(user);

            UserDTO userDto=new UserDTO();
            userDto.setUsername(username);
            userDto.setEmail(oAuth2Response.getEmail());
            userDto.setRole("ROLE_USER");

            return new CustomOAuth2User(userDto);
        } else{
            existData.setEmail(oAuth2Response.getEmail());
            existData.setUsername(oAuth2Response.getName());
            userRepository.save(existData);

            UserDTO userDto=new UserDTO();
            userDto.setUsername(username);
            userDto.setEmail(oAuth2Response.getEmail());
            userDto.setRole("ROLE_USER");

            return new CustomOAuth2User(userDto);
        }
    }
}
