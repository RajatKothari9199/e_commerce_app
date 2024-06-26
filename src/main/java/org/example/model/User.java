package org.example.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Users")
public class User {

    @DynamoDBHashKey(attributeName = "user_id")
    @DynamoDBAutoGeneratedKey
    private String userId;

    @DynamoDBAttribute(attributeName = "username")
    private String username;

    @DynamoDBAttribute(attributeName = "password_hash")
    private String passwordHash;

    @DynamoDBAttribute(attributeName = "token")
    private String token;

    // Default constructor for DynamoDBMapper
    public User() {
    }

    // Constructor with required fields
    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
