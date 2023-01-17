package com.backend.blog.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	private Integer postId;

	private String title;

	private String content;
	private String imageName;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date addeddate;

	private CategoryDto category;

	private UserDto user;

	private List<CommentDto> comments = new ArrayList<>();

}
