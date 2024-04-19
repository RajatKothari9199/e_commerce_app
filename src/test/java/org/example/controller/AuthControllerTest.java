package org.example.controller;

import org.example.controller.AuthController;
import org.example.model.UserDto;
import org.example.model.LoginRequest;
import org.example.model.JwtResponse;
import org.example.service.AuthService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterUser() {
        UserDto userDto = new UserDto("testuser", "password");
        ResponseEntity<?> response = authController.registerUser(userDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAuthenticateUser() {
        LoginRequest loginRequest = new LoginRequest("testuser", "password");
        String token = "jwt_token";
        when(authService.authenticateUser(loginRequest)).thenReturn(token);
        ResponseEntity<?> response = authController.authenticateUser(loginRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new JwtResponse(token), response.getBody());
    }

    @Test
    public void testRefreshToken() {
        String oldToken = "old_token";
        String newToken = "new_token";
        when(authService.refreshToken(oldToken)).thenReturn(newToken);
        ResponseEntity<?> response = authController.refreshToken(oldToken);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new JwtResponse(newToken), response.getBody());
    }

    @Test
    public void testLogoutUser() {
        String token = "jwt_token";
        ResponseEntity<?> response = authController.logoutUser(token);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User logged out successfully", response.getBody());
    }

    @Test
    public void testDeleteUser() {
        String username = "testuser";
        ResponseEntity<?> response = authController.deleteUser(username);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully", response.getBody());
    }

    @Test
    public void testUpdateUserPassword() {
        String username = "testuser";
        String newPassword = "new_password";
        ResponseEntity<?> response = authController.updateUserPassword(username, newPassword);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User password updated successfully", response.getBody());
    }
}
