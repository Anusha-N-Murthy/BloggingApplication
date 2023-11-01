package com.codewithsaurabh.blog_app_apis.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithsaurabh.blog_app_apis.config.AppConstants;
import com.codewithsaurabh.blog_app_apis.entities.Role;
import com.codewithsaurabh.blog_app_apis.entities.User;
import com.codewithsaurabh.blog_app_apis.exceptions.ResourceNotFoundException;
import com.codewithsaurabh.blog_app_apis.payloads.UserDto;
import com.codewithsaurabh.blog_app_apis.repositories.RoleRepo;
import com.codewithsaurabh.blog_app_apis.repositories.UserRepo;
import com.codewithsaurabh.blog_app_apis.services.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
//	ModelMapper is used for map one object to another object
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToUserDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId.toString()));
			 user.setName(userDto.getName());
			 user.setEmail(user.getEmail());
			 user.setPassword(userDto.getPassword());
			 user.setAbout(userDto.getAbout());
		
		User updateUser = this.userRepo.save(user);
		UserDto userDto1 = this.userToUserDto(updateUser);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId.toString()));
		return this.userToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.userToUserDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId.toString()));
		this.userRepo.delete(user);
	}

	public User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		
		return user;
	}


	public UserDto userToUserDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
		return userDto;
	}	
	
	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		
		//encoded the password
		System.out.println("UserServiceImpl -> User password = "+user.getPassword());
		System.out.println("UserServiceImpl -> User Encoded password = "+this.passwordEncoder.encode(user.getPassword()));
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//roles
//		Optional<Role> findById =this.roleRepo.findById(AppConstants.NORMAL_USER);
		Role role =this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User newUser = this.userRepo.save(user);
		
		
		return this.modelMapper.map(newUser, UserDto.class);
		}

	@Override
	public UserDto getUserIdByUsername(String username) {
			User user = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User","username",username));
			return this.userToUserDto(user);
		}
}