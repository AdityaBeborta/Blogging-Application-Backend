package com.backend.blog.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.blog.entities.User;
import com.backend.blog.jwt.JwtAuthRequest;
import com.backend.blog.jwt.JwtAuthResponse;
import com.backend.blog.jwt.JwtTokenHelper;
import com.backend.blog.payloads.UserDto;
import com.backend.blog.repository.UserRepo;
import com.backend.blog.sprinfsecurity.configurations.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private com.backend.blog.service.impl.UserServiceImpl userServiceImpl;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) {
		this.authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());
		// if everything is fine we will generate token
		UserDetails loadUserByUsername = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
		String username = loadUserByUsername.getUsername();
		User user = this.userRepo.findByEmail(username).get();

		String generateToken = this.jwtTokenHelper.generateToken(loadUserByUsername);
		JwtAuthResponse authResponse = new JwtAuthResponse();
		authResponse.setToken(generateToken);
		authResponse.setUserDto(this.modelMapper.map(user, UserDto.class));
		return new ResponseEntity<JwtAuthResponse>(authResponse, HttpStatus.OK);
	}

	private void authenticate(String username, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("invalid password");
		}

	}

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerDto(@Valid @RequestBody UserDto userDto) {
		UserDto registerUser = this.userServiceImpl.registerUser(userDto);
		return new ResponseEntity<UserDto>(registerUser, HttpStatus.CREATED);
	}
}
