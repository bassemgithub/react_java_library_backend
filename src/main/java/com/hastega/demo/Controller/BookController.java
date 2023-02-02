package com.hastega.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    @CrossOrigin
    @PostMapping("/books")
    public void save(@RequestBody Book book){
    	bookService.save(book);
    }
    
    /*@RequestMapping(value="/parameters/countries/delete/{id}" , method={RequestMethod.GET, RequestMethod.DELETE})
    public String delete(@PathVariable Integer id){
    	bookService.delete(id);
        return "redirect:/parameters/countries";
    }*/
    
    @CrossOrigin
    @DeleteMapping(value="/delete/book/{id}" )
    public void delete(@PathVariable Long id){
    	bookService.delete(id);
    }
}