package com.sekarre.helpcenterauth.services.auth;

import com.sekarre.helpcenterauth.DTO.auth.TokenResponse;
import com.sekarre.helpcenterauth.DTO.auth.UserCredentials;
import com.sekarre.helpcenterauth.SecurityContextMockSetup;
import com.sekarre.helpcenterauth.domain.User;
import com.sekarre.helpcenterauth.security.jwt.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.sekarre.helpcenterauth.factories.UserMockFactory.getCurrentUserMock;
import static com.sekarre.helpcenterauth.factories.UserMockFactory.getUserCredentialsMock;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceImplTest extends SecurityContextMockSetup {

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AuthService authService;


    @BeforeEach
    void setUp() {
        super.setUpSecurityContext();
        MockitoAnnotations.openMocks(this);
        authService = new AuthServiceImpl(userDetailsService, jwtTokenUtil, passwordEncoder);
    }

    @Test
    void should_authenticate_user_and_return_token() {
        //given
        final User user = getCurrentUserMock();
        final UserCredentials userCredentials = getUserCredentialsMock();
        when(userDetailsService.loadUserByUsername(any())).thenReturn(user);
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(true);

        //when
        TokenResponse result = authService.getToken(userCredentials);

        //then
        assertNotNull(result);
        verify(userDetailsService, times(1)).loadUserByUsername(userCredentials.getUsername());
        verify(passwordEncoder, times(1)).matches(userCredentials.getPassword(), user.getPassword());
    }

    @Test
    void should_NOT_authenticate_user_and_throw_bad_credentials_exception() {
        //given
        final User user = getCurrentUserMock();
        final UserCredentials userCredentials = getUserCredentialsMock();
        when(userDetailsService.loadUserByUsername(any())).thenReturn(user);
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(false);

        //when + then
        assertThrows(BadCredentialsException.class, () -> authService.getToken(userCredentials));

        verify(passwordEncoder, times(1)).matches(userCredentials.getPassword(), user.getPassword());
        verify(userDetailsService, times(1)).loadUserByUsername(userCredentials.getUsername());
    }
}