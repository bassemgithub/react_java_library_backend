package com.hastega.demo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hastega.demo.Model.User;
import com.hastega.demo.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
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
    void testGetAllUsers() throws Exception {
        List<User> users = Arrays.asList(testUser);
        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].surname").value("Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));

        verify(userService, times(1)).findAll();
    }

    @Test
    void testGetAllUsersEmpty() throws Exception {
        when(userService.findAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(userService, times(1)).findAll();
    }

    @Test
    void testSaveUser() throws Exception {
        doNothing().when(userService).save(any(User.class));

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk());

        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void testGetAllUsersWithMultipleUsers() throws Exception {
        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane");
        user2.setSurname("Smith");
        user2.setEmail("jane.smith@example.com");

        List<User> users = Arrays.asList(testUser, user2);
        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].name").value("Jane"));

        verify(userService, times(1)).findAll();
    }

    @Test
    void testSaveUserWithValidData() throws Exception {
        User newUser = new User();
        newUser.setName("Jane");
        newUser.setSurname("Smith");
        newUser.setEmail("jane.smith@example.com");

        doNothing().when(userService).save(any(User.class));

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk());

        verify(userService, times(1)).save(any(User.class));
    }
}
