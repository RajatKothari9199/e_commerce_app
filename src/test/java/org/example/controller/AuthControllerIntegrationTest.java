package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Main;
import org.example.config.DynamoDBConfig;
import org.example.model.LoginRequest;
import org.example.model.UserDto;
import org.example.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {Main.class, DynamoDBConfig.class}) // Replace YourApplication.class with your main application class
@AutoConfigureMockMvc
@Import(AuthController.class) // Add this line to import AuthController into the test context
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    public void testRegisterUser() throws Exception {
        UserDto userDto = new UserDto("testuser", "password");

        // Mocking the service method call
        doNothing().when(authService).registerUser(any(UserDto.class));

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                // Validate the response code and content
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User registered successfully"));
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest("testuser", "password");
        String fakeToken = "fake_token"; // Assuming you have a fake token for testing

        // Mocking the service method call
        when(authService.authenticateUser(any(LoginRequest.class))).thenReturn(fakeToken);

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginRequest)))
                // Validate the response code and content
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(fakeToken));
    }

    @Test
    public void testRefreshToken() throws Exception {
        String oldToken = "old_token";
        String newToken = "new_token"; // Assuming you have a fake new token for testing

        // Mocking the service method call
        when(authService.refreshToken(oldToken)).thenReturn(newToken);

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/refresh")
                        .header("Authorization", oldToken))
                // Validate the response code and content
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(newToken));
    }

    @Test
    public void testLogoutUser() throws Exception {
        String token = "fake_token";
        String username = "testuser"; // Assuming authService.getUsernameFromToken(token) returns "testuser"

        // Mocking the service method call
        doNothing().when(authService).logoutUser(username);

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/logout")
                        .header("Authorization", token))
                // Validate the response code and content
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User logged out successfully"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        String username = "testuser";

        // Mocking the service method call
        doNothing().when(authService).deleteUser(username);

        // Perform the DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/auth/delete")
                        .param("username", username))
                // Validate the response code and content
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User deleted successfully"));
    }

    @Test
    public void testUpdateUserPassword() throws Exception {
        String username = "testuser";
        String newPassword = "new_password";

        // Mocking the service method call
        doNothing().when(authService).updateUserPassword(username, newPassword);

        // Perform the PUT request
        mockMvc.perform(MockMvcRequestBuilders.put("/api/auth/update")
                        .param("username", username)
                        .param("newPassword", newPassword))
                // Validate the response code and content
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User password updated successfully"));
    }



    // Helper method to convert object to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
