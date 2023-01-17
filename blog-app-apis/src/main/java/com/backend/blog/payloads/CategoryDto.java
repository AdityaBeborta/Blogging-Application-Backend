package com.backend.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDto {

	private Integer categoryId;
	@NotBlank
	@Size(min = 4, message = "minimum size of category title is 4")
	private String categoryTitle;
	@NotBlank
	@Size(min = 10, message = "minimum size of category description should be 10")
	private String categoryDescription;
}
