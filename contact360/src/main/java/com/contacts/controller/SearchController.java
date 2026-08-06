package com.contacts.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.contacts.dao.ContactRepository;
import com.contacts.dao.UserRepository;
import com.contacts.model.Contact;
import com.contacts.model.User;

@RestController
public class SearchController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ContactRepository contactRepository;

	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query, Principal principal){
		
		User user = this.userRepository.getUserByUsername(principal.getName());
//		List<Contact> contacts = this.contactRepository.findByFNameContainingAndUser(query, user);
		List<Contact> contacts = this.contactRepository.findByUserAndFNameContainingIgnoreCaseOrUserAndLNameContainingIgnoreCase(user, query, user, query);
		return ResponseEntity.ok(contacts);
		
	}
	
}
