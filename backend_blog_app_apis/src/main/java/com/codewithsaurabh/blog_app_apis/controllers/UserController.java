package com.codewithsaurabh.blog_app_apis.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithsaurabh.blog_app_apis.payloads.ApiResponse;
import com.codewithsaurabh.blog_app_apis.payloads.UserDto;
import com.codewithsaurabh.blog_app_apis.services.UserService;

@RestController
@RequestMapping("/api/")
public class UserController {
	@Autowired
	private UserService userService;

	// POST-create user
	@PostMapping("users/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}

//	PUT-update user
	@PutMapping("users/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uId) {
		UserDto updateUser = this.userService.updateUser(userDto, uId);
		return ResponseEntity.ok(updateUser);
	}

	
//  Admin-Access only
//	DELETE-delete user
	@PreAuthorize("hasRole('NORMAL')")
	@DeleteMapping("users/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
		this.userService.deleteUser(userId);
//			return ResponseEntity.ok(Map.of("message","User Deleted Successfully!"));
//			return new ResponseEntity(Map.of("message","User Deleted Successfully!"),HttpStatus.OK);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully!", true), HttpStatus.OK);

	}

//	GET-user All
	@GetMapping("users/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return ResponseEntity.ok(this.userService.getAllUsers());
	}

//		GET-user-Single
	@GetMapping("users/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
}