package org.example.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AmazonDynamoDB amazonDynamoDB;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userRepository = new UserRepository(dynamoDBMapper, amazonDynamoDB);
    }

    @Test
    public void testFindByUsername() {
        String username = "testUser";
        String passwordHash = "hashedPassword";

        // Mocking the behavior of DynamoDBMapper's scan method
        PaginatedScanList<User> mockScanList = mock(PaginatedScanList.class);
        when(dynamoDBMapper.scan(eq(User.class), any())).thenReturn(mockScanList);

        // Creating a user with the given username and password hash
        User expectedUser = new User(username, passwordHash);

        // Mocking the behavior of the mockScanList
        when(mockScanList.isEmpty()).thenReturn(false);
        when(mockScanList.get(0)).thenReturn(expectedUser);

        // Mocking the behavior of AmazonDynamoDB's scan method
        ScanResult scanResult = new ScanResult();
        List<Map<String, AttributeValue>> items = new ArrayList<>();
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("username", new AttributeValue(username));
        items.add(item);
        scanResult.setItems(items);
        when(amazonDynamoDB.scan(any())).thenReturn(scanResult);

        // Perform the test
        User result = userRepository.findByUsername(username);

        // Assert the result
        assertEquals(expectedUser, result);
    }


    @Test
    public void testSave() {
        // Create a user object to save
        User user = new User("testUser", "hashedPassword");

        // Test save method
        userRepository.save(user);

        // Verify that the save method is invoked with the correct user object
        verify(dynamoDBMapper, times(1)).save(eq(user));
    }

    @Test
    void testDeleteByUsername() {
        // Define the username to be deleted
        String username = "testuser";
        String passwordHash = "hashedPassword";

        // Create a user object for the mocked result
        User user = new User();
        user.setUsername(username);
        // Mocking the behavior of DynamoDBMapper's scan method
        PaginatedScanList<User> mockScanList = mock(PaginatedScanList.class);
        when(dynamoDBMapper.scan(eq(User.class), any())).thenReturn(mockScanList);
        // Mocking the behavior of the mockScanList
        when(mockScanList.isEmpty()).thenReturn(false);
        User expectedUser = new User(username, passwordHash);
        when(mockScanList.get(0)).thenReturn(expectedUser);


        // Mock findByUsername method to return the user object when "testuser" is provided
        when(userRepository.findByUsername(username)).thenReturn(user);

        // Test deleteByUsername method
        userRepository.deleteByUsername(username);

        // Verify that the delete method is called with the correct user object
        verify(dynamoDBMapper, times(1)).delete(eq(user));
    }


}
