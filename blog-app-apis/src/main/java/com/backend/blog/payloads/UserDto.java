package com.backend.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.backend.blog.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserDto {

	private int id;

	@NotEmpty
	@Size(min = 4, message = "username must be min of 4 characters")
	private String name;
	@Email(message = "Email address is not valid ,Please enter a valid email address", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
	private String email;
	@NotEmpty
	@Size(min = 4, max = 10, message = "password should be minimum of 4 char and maximum of 10 char")

	private String password;
	@NotEmpty(message = "about should not be blank")
	private String about;
	private Set<Role> roles = new HashSet<Role>();

	/// we have to use json ignore in getter only we should not use in setter
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	@JsonProperty
	public void setPassword(String password) {
		this.password=password;
	}
}
