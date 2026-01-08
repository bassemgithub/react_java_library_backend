package com.hastega.demo.Repository;

import com.hastega.demo.Model.Book;
import com.hastega.demo.Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    private User testUser;
    private Book testBook;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setName("John");
        testUser.setSurname("Doe");
        testUser.setEmail("john.doe@test.com");
        testUser = entityManager.persistAndFlush(testUser);

        testBook = new Book();
        testBook.setTitle("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setIsbn("1234567890123");
        testBook.setPlot("Test Plot");
        testBook.setPageNumber(300);
        testBook.setUser(testUser);
    }

    @Test
    void testSaveBook() {
        Book savedBook = bookRepository.save(testBook);

        assertNotNull(savedBook.getId());
        assertEquals("Test Book", savedBook.getTitle());
        assertEquals("Test Author", savedBook.getAuthor());
        assertEquals(testUser.getId(), savedBook.getUser().getId());
    }

    @Test
    void testFindById() {
        Book savedBook = entityManager.persistAndFlush(testBook);

        Optional<Book> found = bookRepository.findById(savedBook.getId());

        assertTrue(found.isPresent());
        assertEquals("Test Book", found.get().getTitle());
    }

    @Test
    void testFindAll() {
        entityManager.persistAndFlush(testBook);

        Book book2 = new Book();
        book2.setTitle("Test Book 2");
        book2.setAuthor("Test Author 2");
        book2.setIsbn("9876543210987");
        book2.setPlot("Test Plot 2");
        book2.setPageNumber(400);
        book2.setUser(testUser);
        entityManager.persistAndFlush(book2);

        List<Book> books = bookRepository.findAll();

        assertEquals(2, books.size());
    }

    @Test
    void testFindByUserIdAndDelteDate() {
        entityManager.persistAndFlush(testBook);

        List<Book> books = bookRepository.findByUserIdAndDelteDate((int) testUser.getId());

        assertEquals(1, books.size());
        assertEquals("Test Book", books.get(0).getTitle());
    }

    @Test
    void testFindByUserIdAndDelteDateWithDeletedBook() {
        testBook.setDeleteDate(new Date());
        entityManager.persistAndFlush(testBook);

        List<Book> books = bookRepository.findByUserIdAndDelteDate((int) testUser.getId());

        assertEquals(0, books.size());
    }

    @Test
    void testFindByUserIdAndDelteDateWithMultipleBooks() {
        entityManager.persistAndFlush(testBook);

        Book book2 = new Book();
        book2.setTitle("Test Book 2");
        book2.setAuthor("Test Author 2");
        book2.setIsbn("9876543210987");
        book2.setPlot("Test Plot 2");
        book2.setPageNumber(400);
        book2.setUser(testUser);
        entityManager.persistAndFlush(book2);

        Book book3 = new Book();
        book3.setTitle("Test Book 3");
        book3.setAuthor("Test Author 3");
        book3.setIsbn("1111111111111");
        book3.setPlot("Test Plot 3");
        book3.setPageNumber(500);
        book3.setUser(testUser);
        book3.setDeleteDate(new Date());
        entityManager.persistAndFlush(book3);

        List<Book> books = bookRepository.findByUserIdAndDelteDate((int) testUser.getId());

        assertEquals(2, books.size());
    }

    @Test
    void testDeleteById() {
        Book savedBook = entityManager.persistAndFlush(testBook);
        Long bookId = savedBook.getId();

        bookRepository.deleteById(bookId);

        Optional<Book> deletedBook = bookRepository.findById(bookId);
        assertFalse(deletedBook.isPresent());
    }

    @Test
    void testUpdateBook() {
        Book savedBook = entityManager.persistAndFlush(testBook);

        savedBook.setTitle("Updated Title");
        savedBook.setPageNumber(500);
        Book updatedBook = bookRepository.save(savedBook);

        assertEquals("Updated Title", updatedBook.getTitle());
        assertEquals(500, updatedBook.getPageNumber());
    }

    @Test
    void testBookWithNullDeleteDate() {
        entityManager.persistAndFlush(testBook);

        List<Book> books = bookRepository.findByUserIdAndDelteDate((int) testUser.getId());

        assertEquals(1, books.size());
        assertNull(books.get(0).getDeleteDate());
    }
}
