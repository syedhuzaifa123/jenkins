package com.contacts.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.contacts.dao.UserRepository;
import com.contacts.model.User;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.getUserByUsername(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("Couldn't found user!");
		}
		
//		UserDetailsImpl userDetailsImpl = new UserDetailsImpl(user);
//		return userDetailsImpl;
		
		return new org.springframework.security.core.userdetails.User(
		        user.getEmail(),
		        user.getPassword(),  // This is the encoded password
		        AuthorityUtils.createAuthorityList(user.getRole())
		    );
	}

}
