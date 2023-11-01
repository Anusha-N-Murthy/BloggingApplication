package com.codewithsaurabh.blog_app_apis.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.codewithsaurabh.blog_app_apis.entities.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int userId;

	@NotEmpty
	@Size(min = 4, message = "Username must be min 4 characters!!")
	private String name;

	@Email(message = "Email address is not valid!!")
	private String email;

	@Size(min = 3, max = 15, message = "Password must be min 3 and maximum 15 characters!!")
	private String password;

	@NotEmpty
	private String about;

	private Set<RoleDto> roles = new HashSet<>();

	// getters and setters...
}
