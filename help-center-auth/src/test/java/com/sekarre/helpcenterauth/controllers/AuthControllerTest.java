package com.sekarre.helpcenterauth.controllers;

import com.sekarre.helpcenterauth.DTO.auth.TokenResponse;
import com.sekarre.helpcenterauth.DTO.auth.UserCredentials;
import com.sekarre.helpcenterauth.services.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.sekarre.helpcenterauth.factories.UserMockFactory.getUserCredentialsMock;
import static com.sekarre.helpcenterauth.testutil.TestUtil.convertObjectToJsonBytes;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    private MockMvc mockMvc;

    private AuthController authController;

    private static final String BASE_URL = "/api/v1/auth";
    private final TokenResponse tokenResponse = new TokenResponse("token");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authController = new AuthController(authService);

        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .build();
    }

    @Test
    void should_return_token_response_with_OK_status() throws Exception {
        //given
        when(authService.getToken(any())).thenReturn(tokenResponse);
        UserCredentials userCredentials = getUserCredentialsMock();
        byte[] content = convertObjectToJsonBytes(userCredentials);

        //when + then
        mockMvc.perform(post(BASE_URL + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(tokenResponse.getToken()));
        verify(authService, times(1)).getToken(any(UserCredentials.class));
    }
}