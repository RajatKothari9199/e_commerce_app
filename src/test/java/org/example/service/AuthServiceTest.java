package org.example.service;

import org.example.model.LoginRequest;
import org.example.model.User;
import org.example.model.UserDto;
import org.example.repository.UserRepository;
import org.example.util.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    public void testRegisterUser() {
        UserDto userDto = new UserDto("testuser", "password");
        authService.registerUser(userDto);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testAuthenticateUser() {
        LoginRequest loginRequest = new LoginRequest("testuser", "password");
        User user = new User("testuser", "$2a$10$LR8VpZgxW4otzt5blbP6reKvwMPT5AMe/F9n3MmQ7tvw0aW2EvKrm");
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("password", user.getPasswordHash())).thenReturn(true);
        when(jwtTokenUtil.generateToken("testuser")).thenReturn("jwt_token");
        String token = authService.authenticateUser(loginRequest);
        assertEquals("jwt_token", token);
    }


    @Test
    public void testRefreshToken() {
        String oldToken = "old_token";
        when(jwtTokenUtil.getUsernameFromToken(oldToken)).thenReturn("testuser");
        when(jwtTokenUtil.generateToken("testuser")).thenReturn("new_token");
        String newToken = authService.refreshToken(oldToken);
        assertEquals("new_token", newToken);
    }

    @Test
    public void testLogoutUser() {
        String token = "jwt_token";
        authService.logoutUser(token);
        // Implement assertions or verify interactions as needed
    }

    @Test
    public void testDeleteUser() {
        String username = "testuser";
        authService.deleteUser(username);
        verify(userRepository, times(1)).deleteByUsername(username);
    }

    @Test
    public void testUpdateUserPassword() {
        String username = "testuser";
        String newPassword = "new_password";
        User user = new User(username, "old_password_hash");
        when(userRepository.findByUsername(username)).thenReturn(user);
        authService.updateUserPassword(username, newPassword);
        verify(userRepository, times(1)).save(user);
    }
}