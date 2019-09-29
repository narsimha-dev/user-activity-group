package com.user.social.communicate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.user.social.communicate.config.JwtTokenUtil;
import com.user.social.communicate.config.JwtUserDetailsService;
import com.user.social.communicate.global.JwtRequest;
import com.user.social.communicate.global.JwtResponse;
import com.user.social.communicate.model.UserDTO;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
//	@GetMapping("/")
//	public  String msg(){
//		return  "yes running";
//	}
	@PostMapping("/oauth/token")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
          System.out.println(authenticationRequest.getUsername()+"  ../api/oauth/token/... "+ authenticationRequest.getPassword() );
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}
	@PostMapping("/user/register")
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user) {
		return ResponseEntity.ok(jwtUserDetailsService.save(user));
	}
	
	private void authenticate(String username, String password) throws Exception {
		System.out.print(username+" .../controller jwt authenticate: "+password);
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			System.out.println("DisabledException is called: ");
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {

			System.out.println("BadCredentialsException is called: ");
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
