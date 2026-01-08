package com.hastega.demo.Repository;

import com.hastega.demo.Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setName("John");
        testUser.setSurname("Doe");
        testUser.setEmail("john.doe@test.com");
    }

    @Test
    void testSaveUser() {
        User savedUser = userRepository.save(testUser);

        assertNotNull(savedUser.getId());
        assertEquals("John", savedUser.getName());
        assertEquals("Doe", savedUser.getSurname());
        assertEquals("john.doe@test.com", savedUser.getEmail());
    }

    @Test
    void testFindById() {
        User savedUser = entityManager.persistAndFlush(testUser);

        Optional<User> found = userRepository.findById(savedUser.getId());

        assertTrue(found.isPresent());
        assertEquals("John", found.get().getName());
        assertEquals("Doe", found.get().getSurname());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<User> found = userRepository.findById(999L);

        assertFalse(found.isPresent());
    }

    @Test
    void testFindAll() {
        entityManager.persistAndFlush(testUser);

        User user2 = new User();
        user2.setName("Jane");
        user2.setSurname("Smith");
        user2.setEmail("jane.smith@test.com");
        entityManager.persistAndFlush(user2);

        List<User> users = userRepository.findAll();

        assertEquals(2, users.size());
    }

    @Test
    void testFindAllEmpty() {
        List<User> users = userRepository.findAll();

        assertEquals(0, users.size());
    }

    @Test
    void testDeleteById() {
        User savedUser = entityManager.persistAndFlush(testUser);
        Long userId = savedUser.getId();

        userRepository.deleteById(userId);

        Optional<User> deletedUser = userRepository.findById(userId);
        assertFalse(deletedUser.isPresent());
    }

    @Test
    void testUpdateUser() {
        User savedUser = entityManager.persistAndFlush(testUser);

        savedUser.setName("UpdatedName");
        savedUser.setSurname("UpdatedSurname");
        savedUser.setEmail("updated@test.com");
        User updatedUser = userRepository.save(savedUser);

        assertEquals("UpdatedName", updatedUser.getName());
        assertEquals("UpdatedSurname", updatedUser.getSurname());
        assertEquals("updated@test.com", updatedUser.getEmail());
    }

    @Test
    void testSaveMultipleUsers() {
        userRepository.save(testUser);

        User user2 = new User();
        user2.setName("Jane");
        user2.setSurname("Smith");
        user2.setEmail("jane.smith@test.com");
        userRepository.save(user2);

        List<User> users = userRepository.findAll();
        assertEquals(2, users.size());
    }

    @Test
    void testUserWithUniqueEmail() {
        entityManager.persistAndFlush(testUser);

        User user2 = new User();
        user2.setName("Jane");
        user2.setSurname("Smith");
        user2.setEmail("john.doe@test.com"); // Same email

        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(user2);
        });
    }
}
