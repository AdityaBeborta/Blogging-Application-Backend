package com.backend.blog.services;

import java.util.List;

import com.backend.blog.payloads.ApiResponse;
import com.backend.blog.payloads.CommentDto;

public interface CommentService {

	// add comment
	public CommentDto addComment(CommentDto commentDto, Integer postId, Integer userId);

	// update comment
	public CommentDto updateComment(CommentDto commentDto, Integer commentId);

	// delete comment
	public ApiResponse deleteComment(Integer commentId, Integer userId);

	// get Comment for a particular post
	public List<CommentDto> getAll(Integer postId);

}
