package com.backend.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.blog.payloads.ApiResponse;
import com.backend.blog.payloads.ApplicationConstants;
import com.backend.blog.payloads.PostDto;
import com.backend.blog.payloads.PostResponse;
import com.backend.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {
	@Autowired
	private PostService postService;

	// add post
	@PostMapping("/users/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDto> savaPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDto postDtoDb = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<>(postDtoDb, HttpStatus.CREATED);

	}

	// get post by user
	@GetMapping("/user/{userId}/post")
	public ResponseEntity<List<PostDto>> getAllPostByUserId(@PathVariable Integer userId) {
		List<PostDto> postByUser = this.postService.getPostByUser(userId);
		return new ResponseEntity<>(postByUser, HttpStatus.OK);
	}

	// get post by category
	@GetMapping("/category/{categoryId}/post")
	public ResponseEntity<List<PostDto>> getAllPostByCategoryId(@PathVariable Integer categoryId) {
		List<PostDto> allByCategory = this.postService.getAllByCategory(categoryId);
		return new ResponseEntity<>(allByCategory, HttpStatus.OK);
	}

	// get single post by post id
	@GetMapping("/postId/{postId}/post")
	public ResponseEntity<PostDto> getASinglePost(@PathVariable Integer postId) {
		PostDto singlePost = this.postService.getSinglePost(postId);
		return new ResponseEntity<PostDto>(singlePost, HttpStatus.OK);
	}

	// get all post
	@GetMapping("/post")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(defaultValue = ApplicationConstants.PAGE_NUMBER, value = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(defaultValue = ApplicationConstants.PAGE_SIZE, value = "pageSize", required = false) Integer pageSize,
			@RequestParam(defaultValue = ApplicationConstants.SORT_BY, value = "sortBy", required = false) String sortBy,
			@RequestParam(defaultValue = ApplicationConstants.SORT_DIRECTION, value = "sortDirection", required = false) String sortDirection) {
		PostResponse allPost = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDirection);
		return new ResponseEntity<PostResponse>(allPost, HttpStatus.OK);
	}

	// delete post by postId
	@DeleteMapping("/postId/{postId}/post")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity(new ApiResponse("Post deleted", true), HttpStatus.OK);
	}

	@PutMapping("/{postId}/post")
	public ResponseEntity<PostDto> updatedUser(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	// search by title
	@GetMapping("/post/{keyword}")
	public ResponseEntity<List<PostDto>> searchPost(@PathVariable String keyword) {
		List<PostDto> searchPost = this.postService.searchPost(keyword);
		return new ResponseEntity<List<PostDto>>(searchPost, HttpStatus.OK);
	}
}
