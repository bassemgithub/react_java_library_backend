package com.hastega.demo.Service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import com.hastega.demo.Model.Book;
import com.hastega.demo.Model.User;
import com.hastega.demo.Repository.BookRepository;
import com.hastega.demo.Repository.UserRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private UserRepository userRepository;

	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	public List<Book> findByUserId(Integer user_id) {	
		//return bookRepository.findByUserId(user_id);
		return bookRepository.findByUserIdAndDelteDate(user_id);
		
	}
	
	public Optional<Book> findByBookId(Long book_id) {
		return bookRepository.findById(book_id);
		
	}
	
	// add a book for a foregien Key user
	public Book save(Long userId, Book book) {
		User user = userRepository.findById(userId).get();
		book.assignUser(user);
		//bookRepository.save(book);
		 Book myBook = bookRepository.saveAndFlush(book);
		
		return myBook;
		 
	}

	public void delete(Long id) {
		bookRepository.deleteById(id);
	}
	
	// update a the delete date in order to delete book from book list
	public void pathcDeleteDate(Long id) {
		Book book = bookRepository.findById(id).orElse(null);
		book.setDeleteDate(new Date(System.currentTimeMillis()));
		bookRepository.save(book);
		
	}
	
	public void pathcNumberofPage(Long id, Map<String, Object> fields) {
		Book book = bookRepository.findById(id).orElse(null);
		int pagenumber = (int) fields.get("page_number");
		book.setPageNumber(pagenumber);
		
		bookRepository.save(book);
	}

	public void editBookInfo(Long id, Book book) {
		Book current  = bookRepository.findById(id).orElse(null);
		current.setAuthor(book.getAuthor());
		current.setCreateDate(new Date(System.currentTimeMillis()));
		current.setIsbn(book.getIsbn());
		current.setPageNumber(book.getPageNumber());
		current.setPlot(book.getPlot());
		current.setTitle(book.getTitle());
		bookRepository.save(current);
		
	}


	
	
	
}
