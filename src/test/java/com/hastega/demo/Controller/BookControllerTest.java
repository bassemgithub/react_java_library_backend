package com.hastega.demo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hastega.demo.Model.Book;
import com.hastega.demo.Model.User;
import com.hastega.demo.Service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
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
    void testGetAllBooks() throws Exception {
        List<Book> books = Arrays.asList(testBook);
        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Book"))
                .andExpect(jsonPath("$[0].author").value("Test Author"));

        verify(bookService, times(1)).findAll();
    }

    @Test
    void testGetBookDetails() throws Exception {
        Long bookId = 1L;
        when(bookService.findByBookId(bookId)).thenReturn(Optional.of(testBook));

        mockMvc.perform(get("/book/details/{book_id}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"))
                .andExpect(jsonPath("$.isbn").value("1234567890123"));

        verify(bookService, times(1)).findByBookId(bookId);
    }

    @Test
    void testGetBooksByUserId() throws Exception {
        Integer userId = 1;
        List<Book> books = Arrays.asList(testBook);
        when(bookService.findByUserId(userId)).thenReturn(books);

        mockMvc.perform(get("/books/{user_id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Book"));

        verify(bookService, times(1)).findByUserId(userId);
    }

    @Test
    void testSaveBook() throws Exception {
        Long userId = 1L;
        when(bookService.save(eq(userId), any(Book.class))).thenReturn(testBook);

        mockMvc.perform(post("/books/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));

        verify(bookService, times(1)).save(eq(userId), any(Book.class));
    }

    @Test
    void testDeleteBook() throws Exception {
        Long bookId = 1L;
        doNothing().when(bookService).delete(bookId);

        mockMvc.perform(delete("/book/delete/{id}", bookId))
                .andExpect(status().isOk());

        verify(bookService, times(1)).delete(bookId);
    }

    @Test
    void testAddDeletedDate() throws Exception {
        Long bookId = 1L;
        doNothing().when(bookService).pathcDeleteDate(bookId);

        mockMvc.perform(patch("/delete/book/{id}", bookId))
                .andExpect(status().isOk());

        verify(bookService, times(1)).pathcDeleteDate(bookId);
    }

    @Test
    void testEditPageNumber() throws Exception {
        Long bookId = 1L;
        Map<String, Object> fields = new HashMap<>();
        fields.put("page_number", 500);

        doNothing().when(bookService).pathcNumberofPage(eq(bookId), anyMap());

        mockMvc.perform(patch("/book/page_number/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fields)))
                .andExpect(status().isOk());

        verify(bookService, times(1)).pathcNumberofPage(eq(bookId), anyMap());
    }

    @Test
    void testEditBookInfo() throws Exception {
        Long bookId = 1L;
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Updated Author");
        updatedBook.setIsbn("9876543210987");
        updatedBook.setPlot("Updated Plot");
        updatedBook.setPageNumber(400);

        doNothing().when(bookService).editBookInfo(eq(bookId), any(Book.class));

        mockMvc.perform(put("/book/edit/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk());

        verify(bookService, times(1)).editBookInfo(eq(bookId), any(Book.class));
    }

    @Test
    void testGetAllBooksEmpty() throws Exception {
        when(bookService.findAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(bookService, times(1)).findAll();
    }

    @Test
    void testGetBookDetailsNotFound() throws Exception {
        Long bookId = 999L;
        when(bookService.findByBookId(bookId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/book/details/{book_id}", bookId))
                .andExpect(status().isOk());

        verify(bookService, times(1)).findByBookId(bookId);
    }
}
