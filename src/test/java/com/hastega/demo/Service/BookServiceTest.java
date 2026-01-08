package com.hastega.demo.Service;

import com.hastega.demo.Model.Book;
import com.hastega.demo.Model.User;
import com.hastega.demo.Repository.BookRepository;
import com.hastega.demo.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookService bookService;

    private Book testBook;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("John");
        testUser.setSurname("Doe");
        testUser.setEmail("john.doe@example.com");

        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setIsbn("1234567890123");
        testBook.setPlot("Test Plot");
        testBook.setPageNumber(300);
        testBook.setUser(testUser);
    }

    @Test
    void testFindAll() {
        List<Book> books = Arrays.asList(testBook);
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testFindByUserId() {
        Integer userId = 1;
        List<Book> books = Arrays.asList(testBook);
        when(bookRepository.findByUserIdAndDelteDate(userId)).thenReturn(books);

        List<Book> result = bookService.findByUserId(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getTitle());
        verify(bookRepository, times(1)).findByUserIdAndDelteDate(userId);
    }

    @Test
    void testFindByBookId() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(testBook));

        Optional<Book> result = bookService.findByBookId(bookId);

        assertTrue(result.isPresent());
        assertEquals("Test Book", result.get().getTitle());
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void testFindByBookIdNotFound() {
        Long bookId = 999L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Optional<Book> result = bookService.findByBookId(bookId);

        assertFalse(result.isPresent());
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void testSaveBook() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(bookRepository.saveAndFlush(any(Book.class))).thenReturn(testBook);

        Book result = bookService.save(userId, testBook);

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        assertEquals(testUser, result.getUser());
        verify(userRepository, times(1)).findById(userId);
        verify(bookRepository, times(1)).saveAndFlush(any(Book.class));
    }

    @Test
    void testDeleteBook() {
        Long bookId = 1L;
        doNothing().when(bookRepository).deleteById(bookId);

        bookService.delete(bookId);

        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    void testPatchDeleteDate() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(testBook));
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        bookService.pathcDeleteDate(bookId);

        assertNotNull(testBook.getDeleteDate());
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(testBook);
    }

    @Test
    void testPatchNumberOfPage() {
        Long bookId = 1L;
        Map<String, Object> fields = new HashMap<>();
        fields.put("page_number", 500);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(testBook));
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        bookService.pathcNumberofPage(bookId, fields);

        assertEquals(500, testBook.getPageNumber());
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(testBook);
    }

    @Test
    void testEditBookInfo() {
        Long bookId = 1L;
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Updated Author");
        updatedBook.setIsbn("9876543210987");
        updatedBook.setPlot("Updated Plot");
        updatedBook.setPageNumber(400);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(testBook));
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        bookService.editBookInfo(bookId, updatedBook);

        assertEquals("Updated Title", testBook.getTitle());
        assertEquals("Updated Author", testBook.getAuthor());
        assertEquals("9876543210987", testBook.getIsbn());
        assertEquals("Updated Plot", testBook.getPlot());
        assertEquals(400, testBook.getPageNumber());
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(testBook);
    }
}
