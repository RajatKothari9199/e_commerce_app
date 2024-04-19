package org.example.service;

import org.example.exception.UserAlreadyExistsException;
import org.example.exception.UserNotFoundException;
import org.example.model.LoginRequest;
import org.example.model.User;
import org.example.model.UserDto;
import org.example.repository.UserRepository;
import org.example.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new UserAlreadyExistsException("User with username " + userDto.getUsername() + " already exists");
        }
        String passwordHash = passwordEncoder.encode(userDto.getPassword());
        User user = new User(userDto.getUsername(), passwordHash);
        userRepository.save(user);
    }

    public String authenticateUser(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new UserNotFoundException("Invalid username or password");
        }
        return jwtTokenUtil.generateToken(user.getUsername());
    }

    public String refreshToken(String oldToken) {
        String username = jwtTokenUtil.getUsernameFromToken(oldToken);
        return jwtTokenUtil.generateToken(username);
    }

    public void logoutUser(String username) {
        // Implement logic to invalidate token or remove user session
    }

    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    public void updateUserPassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User with username " + username + " not found");
        }
        String newPasswordHash = passwordEncoder.encode(newPassword);
        user.setPasswordHash(newPasswordHash);
        userRepository.save(user);
    }

    // Placeholder method, replace with actual implementation using JwtTokenUtil
    public String getUsernameFromToken(String token) {
        return jwtTokenUtil.getUsernameFromToken(token);
    }

}
