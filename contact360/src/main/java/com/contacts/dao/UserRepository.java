package com.contacts.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contacts.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

//	boolean existsByEmail(String email);

	@Query("select u from User u  where u.email = :email")
	public User getUserByUsername(@Param("email") String email);

}
