package com.hastega.demo.Service;

import com.hastega.demo.Model.User;
import com.hastega.demo.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("John");
        testUser.setSurname("Doe");
        testUser.setEmail("john.doe@example.com");
    }

    @Test
    void testFindAll() {
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals("Doe", result.get(0).getSurname());
        assertEquals("john.doe@example.com", result.get(0).getEmail());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindAllEmpty() {
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testSaveUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        userService.save(testUser);

        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void testSaveNewUser() {
        User newUser = new User();
        newUser.setName("Jane");
        newUser.setSurname("Smith");
        newUser.setEmail("jane.smith@example.com");

        when(userRepository.save(any(User.class))).thenReturn(newUser);

        userService.save(newUser);

        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void testFindAllWithMultipleUsers() {
        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane");
        user2.setSurname("Smith");
        user2.setEmail("jane.smith@example.com");

        List<User> users = Arrays.asList(testUser, user2);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals("Jane", result.get(1).getName());
        verify(userRepository, times(1)).findAll();
    }
}
