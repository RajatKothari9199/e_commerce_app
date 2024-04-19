package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testConstructorAndGetters() {
        String username = "testuser";
        String passwordHash = "passwordhash123";

        User user = new User(username, passwordHash);

        assertNotNull(user); // Assuming userId is auto-generated
        assertEquals(username, user.getUsername());
        assertEquals(passwordHash, user.getPasswordHash());
        assertNull(user.getToken()); // Token should initially be null
    }

    @Test
    public void testSetters() {
        User user = new User();

        String userId = "123456";
        String username = "testuser";
        String passwordHash = "passwordhash123";
        String token = "jwt_token";

        user.setUserId(userId);
        user.setUsername(username);
        user.setPasswordHash(passwordHash);
        user.setToken(token);

        assertEquals(userId, user.getUserId());
        assertEquals(username, user.getUsername());
        assertEquals(passwordHash, user.getPasswordHash());
        assertEquals(token, user.getToken());
    }
}
