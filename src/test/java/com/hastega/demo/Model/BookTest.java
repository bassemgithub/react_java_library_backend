package com.hastega.demo.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    private Book book;
    private User user;

    @BeforeEach
    void setUp() {
        book = new Book();
        user = new User();
        user.setId(1L);
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john.doe@example.com");
    }

    @Test
    void testBookIdGetterAndSetter() {
        book.setId(1L);
        assertEquals(1L, book.getId());
    }

    @Test
    void testBookTitleGetterAndSetter() {
        book.setTitle("Test Book");
        assertEquals("Test Book", book.getTitle());
    }

    @Test
    void testBookAuthorGetterAndSetter() {
        book.setAuthor("Test Author");
        assertEquals("Test Author", book.getAuthor());
    }

    @Test
    void testBookIsbnGetterAndSetter() {
        book.setIsbn("1234567890123");
        assertEquals("1234567890123", book.getIsbn());
    }

    @Test
    void testBookPlotGetterAndSetter() {
        book.setPlot("Test Plot");
        assertEquals("Test Plot", book.getPlot());
    }

    @Test
    void testBookPageNumberGetterAndSetter() {
        book.setPageNumber(300);
        assertEquals(300, book.getPageNumber());
    }

    @Test
    void testBookCreateDateIsInitialized() {
        assertNotNull(book.getCreateDate());
    }

    @Test
    void testBookCreateDateGetterAndSetter() {
        Date testDate = new Date();
        book.setCreateDate(testDate);
        assertEquals(testDate, book.getCreateDate());
    }

    @Test
    void testBookDeleteDateGetterAndSetter() {
        Date testDate = new Date();
        book.setDeleteDate(testDate);
        assertEquals(testDate, book.getDeleteDate());
    }

    @Test
    void testBookDeleteDateIsNullByDefault() {
        assertNull(book.getDeleteDate());
    }

    @Test
    void testBookUserGetterAndSetter() {
        book.setUser(user);
        assertEquals(user, book.getUser());
        assertEquals(1L, book.getUser().getId());
    }

    @Test
    void testBookAssignUser() {
        book.assignUser(user);
        assertEquals(user, book.getUser());
    }

    @Test
    void testBookAssignUserSetsRelationship() {
        book.assignUser(user);
        assertNotNull(book.getUser());
        assertEquals("John", book.getUser().getName());
        assertEquals("Doe", book.getUser().getSurname());
    }

    @Test
    void testBookWithAllFields() {
        book.setId(1L);
        book.setTitle("Complete Book");
        book.setAuthor("Complete Author");
        book.setIsbn("9781234567890");
        book.setPlot("Complete Plot");
        book.setPageNumber(500);
        book.setUser(user);
        
        assertEquals(1L, book.getId());
        assertEquals("Complete Book", book.getTitle());
        assertEquals("Complete Author", book.getAuthor());
        assertEquals("9781234567890", book.getIsbn());
        assertEquals("Complete Plot", book.getPlot());
        assertEquals(500, book.getPageNumber());
        assertNotNull(book.getUser());
    }
}
