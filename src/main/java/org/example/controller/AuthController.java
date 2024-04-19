package org.example.controller;

import org.example.model.UserDto;
import org.example.model.LoginRequest;
import org.example.model.JwtResponse;
import org.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        authService.registerUser(userDto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        String token = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String oldToken) {
        String newToken = authService.refreshToken(oldToken);
        return ResponseEntity.ok(new JwtResponse(newToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestHeader("Authorization") String token) {
        String username = authService.getUsernameFromToken(token); // Assuming authService provides this method
        authService.logoutUser(username);
        return ResponseEntity.ok("User logged out successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam String username) {
        authService.deleteUser(username);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUserPassword(@RequestParam String username, @RequestParam String newPassword) {
        authService.updateUserPassword(username, newPassword);
        return ResponseEntity.ok("User password updated successfully");
    }
}
