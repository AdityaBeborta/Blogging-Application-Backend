package com.backend.blog.services;

import java.util.List;

import com.backend.blog.entities.Post;
import com.backend.blog.payloads.PostDto;
import com.backend.blog.payloads.PostResponse;

public interface PostService {

	// create post
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);;

	// update post
	PostDto updatePost(PostDto postDto, Integer postId);

	// delete
	void deletePost(Integer postId);

	// get All Post
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

	// get single post
	PostDto getSinglePost(Integer postId);

	// get all post by category
	List<PostDto> getAllByCategory(Integer categoryId);

	// get all post by user
	List<PostDto> getPostByUser(Integer postId);

	// search post
	List<PostDto> searchPost(String keyword);
}
