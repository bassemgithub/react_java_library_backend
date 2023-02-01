package com.hastega.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hastega.demo.Model.Book;
import com.hastega.demo.Service.BookService;


@RestController
public class BookController {

	@Autowired
	BookService bookService;

	@CrossOrigin
	@GetMapping("/books")
	public List getAll(Model model) {

		List<Book> books = bookService.findAll();
		model.addAttribute("books", books);
		return books;
	}
	
    @GetMapping("/books/{id}")
    @ResponseBody
    public List getCountry(@PathVariable Integer id){
        return bookService.findByUserId(id);
    }
}