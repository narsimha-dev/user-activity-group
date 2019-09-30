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
		UserDao user= userRepository.findByusername(username);
		if(user !=null) {
			return new User(user.getUsername(),user.getPassword(),new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
	
  public UserDao save(UserDTO userDataSave) {
	  UserDao newUser= new UserDao();
	  newUser.setUsername(userDataSave.getUsername());
	  newUser.setPassword(passwordEncoder.encode(userDataSave.getPassword()));
	  return userRepository.save(newUser);
  }
}
