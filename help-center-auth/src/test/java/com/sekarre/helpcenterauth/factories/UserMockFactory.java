package com.sekarre.helpcenterauth.factories;

import com.sekarre.helpcenterauth.DTO.auth.UserCredentials;
import com.sekarre.helpcenterauth.domain.User;

public class UserMockFactory {

    public static User getCurrentUserMock() {
        return User.builder()
                .username("login")
                .password("password")
                .build();
    }

    public static UserCredentials getUserCredentialsMock() {
        return UserCredentials.builder()
                .username("login")
                .password("password")
                .build();
    }
}