package com.hastega.demo.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hastega.demo.Model.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	

	//List<Book> findByUserId(Integer id);
	
	@Query(value = "SELECT * FROM book where user_id = ?1 AND delete_date IS NULL", nativeQuery = true)
	List<Book> findByUserIdAndDelteDate(Integer id);

}
