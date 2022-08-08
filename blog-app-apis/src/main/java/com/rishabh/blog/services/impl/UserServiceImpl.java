package com.rishabh.blog.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rishabh.blog.config.ApplicationConstants;
import com.rishabh.blog.entities.Role;
import com.rishabh.blog.entities.User;
import com.rishabh.blog.exceptions.ResourceNotFoundException;
import com.rishabh.blog.payloads.UserDto;
import com.rishabh.blog.repositories.RoleRepo;
import com.rishabh.blog.repositories.UserRepo;
import com.rishabh.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		
		User savedUser = this.userRepo.save(user);
		
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User"," id ", userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updatedUser = this.userRepo.save(user);
		
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
		
		UserDto fetchedUserDto = this.userToDto(user);
		
		return fetchedUserDto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users = this.userRepo.findAll();
		//learn to user stram apis here
		List<UserDto> userDtos = new ArrayList<>();
		
		for(User u:users) {
			userDtos.add(this.userToDto(u));
		}
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User"," Id ", userId));
		
		this.userRepo.delete(user);
		
		return;
	}
	
	//This can be done using model mapper library
	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		
		return user;
	}
	
	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		
		
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
		
		return userDto;
	}

	@Override
	public UserDto registerUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		
		//Encode the Password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//By Default assume the registered role to be normal user
		Role role = this.roleRepo.findById(ApplicationConstants.NORMAL_USER_ROLE_ID).orElseThrow(()->new ResourceNotFoundException("Role","Role Id"+501,0));
		
		user.getRoles().add(role);
		
		User registeredUser = this.userRepo.save(user);
		
		return this.modelMapper.map(registeredUser, UserDto.class);
	}

}
