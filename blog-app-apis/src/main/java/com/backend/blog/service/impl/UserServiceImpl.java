package com.backend.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.blog.entities.Role;
import com.backend.blog.entities.User;
import com.backend.blog.exceptions.ResourceNotFoundException;
import com.backend.blog.exceptions.UserAlreadyExistException;
import com.backend.blog.payloads.ApplicationConstants;
import com.backend.blog.payloads.UserDto;
import com.backend.blog.repository.RoleRepo;
import com.backend.blog.repository.UserRepo;
import com.backend.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.userDtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToUserDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User oldUser = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "Id", userId));
		oldUser.setName(userDto.getName());
		oldUser.setEmail(userDto.getEmail());
		oldUser.setAbout(userDto.getAbout());
		oldUser.setPassword(userDto.getPassword());
		User updatedUser = this.userRepo.save(oldUser);
		return this.userToUserDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "Id", userId));
		return this.userToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> allUsers = this.userRepo.findAll();
		List<UserDto> users = allUsers.stream().map(user -> this.userToUserDto(user)).collect(Collectors.toList());

		return users;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "Id", userId));
		this.userRepo.delete(user);
	}

	private User userDtoToUser(UserDto userDto) {
//		User user = new User(userDto.getId(), userDto.getName(), userDto.getEmail(), userDto.getPassword(),
//				userDto.getAbout());
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}

	private UserDto userToUserDto(User user) {
//		UserDto userDto = new UserDto(user.getId(), user.getName(),
		// user.getEmail(), user.getPassword(),
//		user.getAbout());
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

	public UserDto registerUser(UserDto userDto) {
		User user = this.userDtoToUser(userDto);
		String email = userDto.getEmail();
		String findByUserName = this.userRepo.getByEmail(email);
		System.out.println(findByUserName);
		if (findByUserName == null) {
			Role role = this.roleRepo.findById(ApplicationConstants.ROLE_NORMAL).get();
			// USER can HAVE two ROLES, Add ROLE references in the USER
			user.getRoles().add(role);
			user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
			User savedUser = this.userRepo.save(user);
			return this.userToUserDto(savedUser);
		} else {
			throw new UserAlreadyExistException("user already exist");
		}
	}

}
