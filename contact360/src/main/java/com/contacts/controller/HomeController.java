package com.contacts.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.contacts.dao.UserRepository;
import com.contacts.helper.Message;
import com.contacts.model.User;

@Controller
public class HomeController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping(path = "/")
	public String home(Model model) {
		model.addAttribute("title", "Contacts360 - Homepage");
		return "home";
	}

	@GetMapping(path = "/about")
	public String about(Model model) {
		model.addAttribute("title", "Contacts360 - About Us");
		return "about";
	}

	@GetMapping(path = "/signin")
	public String login(Model model) {
		model.addAttribute("title", "Contacts360 - Login");
		model.addAttribute("user", new User());
		return "login";
	}

	@GetMapping(path = "/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Contacts360 - Registeration");
		model.addAttribute("user", new User());
		return "signup";
	}

	@PostMapping(path = "/register")
	public String register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model) {
		System.out.println("Agreement: " + agreement);
		System.out.println("User: " + user);
		System.out.println("Password Length: " + user.getPassword().length());

		try {

			if (!agreement) {
				System.out.println("Terms and conditions must be checked.");
				model.addAttribute("user", user);
				model.addAttribute("msg", new Message("You must accept the terms and conditions.", "alert-danger"));
				return "signup";
			}

			// Check password length manually before encoding
			if (user.getPassword().length() < 6 || user.getPassword().length() > 20) {
				model.addAttribute("user", user);
				model.addAttribute("msg", new Message("Password must be between 6 and 20 characters.", "alert-danger"));
				return "signup";
			}

			if (bindingResult.hasErrors()) {
				System.out.println("Validation errors: " + bindingResult);
				model.addAttribute("user", user);
				return "signup";
			}

			user.setImageUrl("default.png");
			user.setRole("ROLE_USER");
			user.setStatus(true);

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			User result = userRepository.save(user);
			model.addAttribute("user", new User());
			model.addAttribute("msg", new Message("User registered!", "alert-success"));

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", new Message("Something went wrong! " + e.getMessage(), "alert-danger"));
		}

		return "signup";
	}

	@GetMapping(path = "/test")
	@ResponseBody
	public String test() {

		User user = new User();
		user.setName("Muhammad Umar");
		user.setEmail("umar@gmail.com");
		userRepository.save(user);

		return "Working";
	}
}
