package com.user.social.communicate.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.social.communicate.model.UserDTO;
import com.user.social.communicate.model.UserDao;
import com.user.social.communicate.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("=====usernames is:==== "+username);
		UserDao user= userRepository.findByusername(username);
		System.out.println(user.getUsername()+"====== JwtUserDetailsService===== "+user.getPassword());
		System.out.println("user object is get: "+user);
		if(user !=null) {
		//	System.out.println("calling try block");
		 //if ("narsi".equals(username)) {
			//return new User("narsi", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",new ArrayList<>());
			return new User(user.getUsername(),user.getPassword(),new ArrayList<>());
		} else {
			System.out.println("BadCredentialsException");
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
	
  public UserDao save(UserDTO userDataSave) {
		System.out.println("userDataSave  "+userDataSave);
	  UserDao newUser= new UserDao();
	  System.out.println("UaerDao pass username "+ userDataSave.getUsername());
	  System.out.println("UserDao pass password: "+ userDataSave.getUsername());
	  newUser.setUsername(userDataSave.getUsername());
	  newUser.setPassword(passwordEncoder.encode(userDataSave.getPassword()));
	  return userRepository.save(newUser);
  }
}
