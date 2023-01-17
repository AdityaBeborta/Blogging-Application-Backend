package com.backend.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.blog.entities.Comment;
import com.backend.blog.entities.Post;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
	public List<Comment> findByPost(Post post);
}
