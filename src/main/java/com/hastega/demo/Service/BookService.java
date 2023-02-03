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
	
	// add a book for a foregien Key user
	public void save(Long userId, Book book) {
		User user = userRepository.findById(userId).get();
		book.assignUser(user);
		bookRepository.save(book);
	}

	public void delete(Long id) {
		bookRepository.deleteById(id);
	}
	
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


	
	
	
}
