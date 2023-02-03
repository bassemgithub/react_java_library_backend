package com.hastega.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hastega.demo.Model.Book;
import com.hastega.demo.Service.BookService;


@CrossOrigin(origins ="http://localhost:3000")
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
	
	
	// get books for specific user
    @GetMapping("/books/{id}")
    @ResponseBody
    public List getBook(@PathVariable Integer id){
        return bookService.findByUserId(id);
    }
    
    
    //add book
    @CrossOrigin
    @PostMapping("/books/{userId}")
    public void save(@PathVariable Long userId, @RequestBody Book book){
    	bookService.save(userId, book);
    }
    
    // delete a book by id 
    @CrossOrigin
    @DeleteMapping(value="/book/delete/{id}" )
    public void delete(@PathVariable Long id){
    	bookService.delete(id);
    }
    
    // delete a book by adding deleted date  
    @CrossOrigin
    @PatchMapping(value="/delete/book/{id}" )
    public void addDeletedDate(@PathVariable Long id){
    	bookService.pathcDeleteDate(id);
    }
    
    // edit page number 
    @PatchMapping("/book/page_number/{id}")
    public void editPageNumber(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
    	bookService.pathcNumberofPage(id, fields);
    	
    }
    
    
    
    
}