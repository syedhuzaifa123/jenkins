package com.contacts.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contacts.model.Contact;
import com.contacts.model.User;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

	@Query("from Contact as c where c.user.id =:userId")
	public Page<Contact> findContactsByUser(@Param("userId") int userId, Pageable pageable);

//	public List<Contact> findByFNameContainingAndUser(String name, User user);

	List<Contact> findByUserAndFNameContainingIgnoreCaseOrUserAndLNameContainingIgnoreCase(User user1, String fName,
			User user2, String lName);

}
