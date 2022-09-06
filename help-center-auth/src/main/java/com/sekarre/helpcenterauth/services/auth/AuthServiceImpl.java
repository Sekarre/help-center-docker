package com.sekarre.helpcenterauth.services.auth;

import com.sekarre.helpcenterauth.DTO.auth.TokenResponse;
import com.sekarre.helpcenterauth.DTO.auth.UserCredentials;
import com.sekarre.helpcenterauth.domain.User;
import com.sekarre.helpcenterauth.security.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenResponse getToken(UserCredentials userCredentials) {
        User user = (User) userDetailsService.loadUserByUsername(userCredentials.getUsername());
        if (passwordEncoder.matches(userCredentials.getPassword(), user.getPassword())) {
            return new TokenResponse(jwtTokenUtil.generateAccessToken(user));
        }
        throw new BadCredentialsException("Given credentials are invalid");
    }
}