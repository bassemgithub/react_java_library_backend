package com.hastega.demo.Model;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;
/*import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
*/
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity 
public class Book implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private long id;
	
	
	@Column(nullable = false, length = 20)
	private String title;
	
	@Column(nullable = false, length = 20)
	private String author;
	
	@Column(nullable = false, unique = true, length = 20)
	private String isbn;
	
	@Column(nullable = false, length = 20)
	private String plot;
	
	private Date createDate = new Date(System.currentTimeMillis());
	
	@Column(nullable = true)
	private Date deleteDate;
	
	@Column(name = "page_number")
	private int pageNumber;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName= "id")
	private User user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void assignUser(User user) {
		this.user = user;
		
	}

	
	
	
}

