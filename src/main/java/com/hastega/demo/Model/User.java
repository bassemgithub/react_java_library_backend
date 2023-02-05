package com.hastega.demo.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;
/*import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
*/
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
//public class User implements Serializable {
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private long id;

	@Column(nullable = false, length = 20)
	private String name;

	@Column(nullable = false, length = 20)
	private String surname;

	@Column(nullable = false, length = 20 ,unique = true)
	private String email;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Book> books;

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
