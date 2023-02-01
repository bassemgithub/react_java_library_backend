package com.hastega.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hastega.demo.Model.User;
import com.hastega.demo.Service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@CrossOrigin
	@GetMapping("/users")
	public List getAll(Model model) {

		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		return users;
	}
}