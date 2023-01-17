package com.backend.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.blog.payloads.ApiResponse;
import com.backend.blog.payloads.CommentDto;
import com.backend.blog.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/post/{postId}/user/{userId}/comment")
	public ResponseEntity<CommentDto> postComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId,
			@PathVariable Integer userId) {
		CommentDto addComment = this.commentService.addComment(commentDto, postId, userId);
		return new ResponseEntity<CommentDto>(addComment, HttpStatus.CREATED);
	}

	@PutMapping("/post/comment/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto,
			@PathVariable Integer commentId) {
		CommentDto updateComment = this.commentService.updateComment(commentDto, commentId);
		return new ResponseEntity<CommentDto>(updateComment, HttpStatus.OK);
	}

	@DeleteMapping("/comment/{commentId}/user/{userId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId, @PathVariable Integer userId) {
		ApiResponse apiResponse = this.commentService.deleteComment(commentId, userId);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
}
