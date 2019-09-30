package com.user.social.communicate.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.social.communicate.exception.ResourceNotFoundException;
import com.user.social.communicate.model.Post;
import com.user.social.communicate.model.UserDao;
import com.user.social.communicate.repository.PostRepository;
import com.user.social.communicate.repository.UserRepository;

import antlr.collections.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostController {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;

   // Every Request is verifying
	public UserDao getUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = "";
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		UserDao userDao=userRepository.findByusername(userName);
		return userDao;
	}
	@GetMapping(value = "/posts")
	public java.util.List<Post> getAllPosts() {
		return postRepository.findByuser(getUserName());
	}

	@PostMapping(value = "/posts/add", produces = "application/json")
	public Post createPost(@Valid @RequestBody Post post) {

		post.setUser(getUserName());
		return postRepository.save(post);
	}
    private boolean validPostId(Long postId) {
		 java.util.List<Post> items=postRepository.findByuser(getUserName());
		 boolean postIdIsValiable= items.stream().anyMatch(id->id.getId().equals(postId));
		  Boolean pId= items.contains(postId);
		  return postIdIsValiable;
 	}
	@PutMapping(value = "/posts/{postId}", produces = "application/json")
	public Post updatePost(@PathVariable Long postId, @Valid @RequestBody Post postRequest) {
		try {		
 		if(validPostId(postId)) {
		return postRepository.findById(postId).map(post -> {
			post.setTitle(postRequest.getTitle());
			post.setDescription(postRequest.getDescription());
			post.setContent(postRequest.getContent());
			return postRepository.save(post);
		}).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
	}
		else {
		  System.err.println("some thing is wrong");
		
		 }
		 }catch (Exception e) {
			/* TODO: handle exception */
			 System.err.println(e);
		}
		return postRequest;
	}

	@DeleteMapping(value = "/posts/{postId}", produces = "application/json")
	public ResponseEntity<?> deletePost(@PathVariable Long postId) {
		return postRepository.findById(postId).map(post -> {
			postRepository.delete(post);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
	}

}
