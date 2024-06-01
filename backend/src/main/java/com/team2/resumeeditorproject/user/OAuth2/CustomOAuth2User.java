package com.team2.resumeeditorproject.user.OAuth2;

import com.team2.resumeeditorproject.user.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User{

    private final UserDTO userDTO;

    public CustomOAuth2User(UserDTO userDTO) {

        this.userDTO = userDTO;
    }

    public Map<String, Object> getAttributes() {

        return null;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return userDTO.getLogin().getValue();
            }
        });

        return collection;
    }

    public String getUsername() {

        return userDTO.getUsername();
    }
}

