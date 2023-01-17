package com.backend.blog.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.blog.entities.Comment;
import com.backend.blog.entities.Post;
import com.backend.blog.entities.User;
import com.backend.blog.exceptions.ResourceNotFoundException;
import com.backend.blog.payloads.ApiResponse;
import com.backend.blog.payloads.CommentDto;
import com.backend.blog.payloads.PostDto;
import com.backend.blog.payloads.UserDto;
import com.backend.blog.repository.CommentRepo;
import com.backend.blog.repository.PostRepo;
import com.backend.blog.repository.UserRepo;
import com.backend.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto addComment(CommentDto commentDto, Integer postId, Integer userId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("comment", "commentId", commentId));
		comment.setContent(commentDto.getContent());
		this.commentRepo.save(comment);
		CommentDto updatedComment = this.modelMapper.map(comment, CommentDto.class);
		return updatedComment;
	}

	@Override
	public ApiResponse deleteComment(Integer commentId, Integer userId) {
		ApiResponse apiResponse = null;
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("comment", "commentId", commentId));
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
		if (comment.getUser().getId() == user.getId()) {
			this.commentRepo.delete(comment);
			apiResponse = new ApiResponse("comment deleted successfuly", true);
		} else {
			apiResponse = new ApiResponse("this comment does not belong to you", false);
		}
		return apiResponse;
	}

	@Override
	public List<CommentDto> getAll(Integer postId) {
		// TODO Auto-generated method stub
		return null;
	}

}
