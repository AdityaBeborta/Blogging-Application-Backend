package com.backend.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.blog.payloads.ApiResponse;
import com.backend.blog.payloads.UserDto;
import com.backend.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserController {

	@Autowired
	private UserService userService;

	// post-create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		// in order to enable the validations we need to use @Valid
		UserDto createdUser = this.userService.createUser(userDto);
		return new ResponseEntity<UserDto>(createdUser, HttpStatus.CREATED);
	}

	// put-update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUser);
	}

	// delete-delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUserById(@PathVariable Integer userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity(new ApiResponse("user deleted", true), HttpStatus.OK);
	}

	// get-get user by userId
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserByUserId(@PathVariable Integer userId) {
		UserDto userDto = this.userService.getUserById(userId);
		return ResponseEntity.ok(userDto);
	}

	// get-get all users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser() {
		List<UserDto> allUser = this.userService.getAllUser();
		return ResponseEntity.ok(allUser);
	}
}