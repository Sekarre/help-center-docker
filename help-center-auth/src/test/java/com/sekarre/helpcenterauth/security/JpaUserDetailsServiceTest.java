package com.sekarre.helpcenterauth.security;

import com.sekarre.helpcenterauth.domain.User;
import com.sekarre.helpcenterauth.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

import static com.sekarre.helpcenterauth.factories.UserMockFactory.getCurrentUserMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JpaUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userDetailsService = new JpaUserDetailsService(userRepository);
    }

    @Test
    void should_load_user_by_username_and_return_user_details() {
        //given
        final String username = "username";
        final User user = getCurrentUserMock();

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.ofNullable(user));

        //when
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        //then
        assertNotNull(userDetails);
        assertEquals(userDetails, user);
        verify(userRepository, times(1)).findByUsername(username);
    }
}