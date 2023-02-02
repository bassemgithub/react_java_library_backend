package com.hastega.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hastega.demo.Model.Book;
import com.hastega.demo.Repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	public List<Book> findByUserId(Integer user_id) {
		return bookRepository.findByUserId(user_id);
	}

	public void save(Book book) {
		bookRepository.save(book);
	}

	public void delete(Long id) {
		bookRepository.deleteById(id);
	}
}
