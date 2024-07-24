package com.team2.resumeeditorproject.user.dto;

import com.team2.resumeeditorproject.user.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

//CustomUserDetailsService에 데이터를 넘겨주기 위한 클래스
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection=new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return collection;
    }

    public Long getUserNo() {
        return user.getUserNo();
    }

    public int getMode() {
        return user.getMode();
    }

    public String getRole() {
        return user.getRole();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정 만료되었는지
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠겨있지않은지
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 자격증명(비밀번호 등) 만료되지 않았는지
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 되었는지
    @Override
    public boolean isEnabled() {
        if ("ROLE_BLACKLIST".equals(user.getRole())) {
            Date reactivationDate = getReactivationDate();
            if (reactivationDate != null) {
                return new Date().after(reactivationDate);
            }
        }
        // del_date가 null이 아니면 계정이 비활성화된 상태로 간주하고 false 반환
        return user.getDeletedDate() == null;
    }

    public Date getReactivationDate() {
        if (user.getDeletedDate() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(user.getDeletedDate());
            calendar.add(Calendar.DATE, 60);
            return calendar.getTime();
        }
        return null;
    }
}
