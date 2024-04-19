package org.example.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private final DynamoDBMapper dynamoDBMapper;
    private final AmazonDynamoDB amazonDynamoDB;

    @Autowired
    public UserRepository(DynamoDBMapper dynamoDBMapper, AmazonDynamoDB amazonDynamoDB) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.amazonDynamoDB = amazonDynamoDB;
    }

    public User findByUsername(String username) {
//        DynamoDBMapper mapper = new DynamoDBMapper(this.amazonDynamoDB);
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":username", new AttributeValue().withS(username));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("username = :username")
                .withExpressionAttributeValues(expressionAttributeValues);
        PaginatedScanList<User> result = this.dynamoDBMapper.scan(User.class, scanExpression);
        return result.isEmpty() ? null : result.get(0);
    }

    public void save(User user) {
        dynamoDBMapper.save(user);
    }

    public void deleteByUsername(String username) {
        User user = findByUsername(username);
        if (user != null) {
            dynamoDBMapper.delete(user);
        }
    }
}
