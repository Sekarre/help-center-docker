package com.sekarre.helpcenterauth.services.auth;


import com.sekarre.helpcenterauth.DTO.auth.TokenResponse;
import com.sekarre.helpcenterauth.DTO.auth.UserCredentials;

public interface AuthService {

    TokenResponse getToken(UserCredentials userCredentials);
}