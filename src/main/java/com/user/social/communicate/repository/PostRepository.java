package com.user.social.communicate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.social.communicate.model.Post;
import com.user.social.communicate.model.UserDao;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	Post findByid(Long userId);
	
	List<Post> findByuser(UserDao user);
}
