package com.hastega.demo.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testUserIdGetterAndSetter() {
        user.setId(1L);
        assertEquals(1L, user.getId());
    }

    @Test
    void testUserNameGetterAndSetter() {
        user.setName("John");
        assertEquals("John", user.getName());
    }

    @Test
    void testUserSurnameGetterAndSetter() {
        user.setSurname("Doe");
        assertEquals("Doe", user.getSurname());
    }

    @Test
    void testUserEmailGetterAndSetter() {
        user.setEmail("john.doe@example.com");
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    void testUserBooksGetterAndSetter() {
        List<Book> books = new ArrayList<>();
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");
        books.add(book1);
        
        user.setBooks(books);
        
        assertNotNull(user.getBooks());
        assertEquals(1, user.getBooks().size());
        assertEquals("Book 1", user.getBooks().get(0).getTitle());
    }

    @Test
    void testUserWithEmptyBooks() {
        List<Book> books = new ArrayList<>();
        user.setBooks(books);
        
        assertNotNull(user.getBooks());
        assertEquals(0, user.getBooks().size());
    }

    @Test
    void testUserWithMultipleBooks() {
        List<Book> books = new ArrayList<>();
        
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");
        books.add(book1);
        
        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book 2");
        books.add(book2);
        
        user.setBooks(books);
        
        assertEquals(2, user.getBooks().size());
        assertEquals("Book 1", user.getBooks().get(0).getTitle());
        assertEquals("Book 2", user.getBooks().get(1).getTitle());
    }

    @Test
    void testUserWithAllFields() {
        user.setId(1L);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john.doe@example.com");
        
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Sample Book");
        books.add(book);
        user.setBooks(books);
        
        assertEquals(1L, user.getId());
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals("john.doe@example.com", user.getEmail());
        assertNotNull(user.getBooks());
        assertEquals(1, user.getBooks().size());
    }
}
