package com.backend.blog.jwt;

import com.backend.blog.payloads.UserDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtAuthResponse {

	private String token;
	
	private UserDto userDto;
}
