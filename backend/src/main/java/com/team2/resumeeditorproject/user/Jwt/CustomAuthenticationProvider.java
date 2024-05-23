package com.team2.resumeeditorproject.user.Jwt;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        try {
            return super.authenticate(auth);
        } catch (UsernameNotFoundException ex) {
            throw ex;
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid username or password", ex);
        }
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        try {
            super.additionalAuthenticationChecks(userDetails, authentication);
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid username or password", ex);
        }
    }
}
