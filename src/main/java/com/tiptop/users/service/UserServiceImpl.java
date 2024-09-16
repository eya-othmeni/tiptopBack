package com.tiptop.users.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tiptop.users.dto.UserDTO;
import com.tiptop.users.entities.ROLES;
import com.tiptop.users.repos.ITicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tiptop.users.entities.Role;
import com.tiptop.users.entities.User;
import com.tiptop.users.repos.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ITicketRepository ticketRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Override
	public User saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRole(ROLES.USER);
		return userRepository.save(user);
	}

	@Override
	public User findUserByUsername(String username) {
			return userRepository.findByUsername(username);
	}

	@Override
	public Role addRole(Role role) {
			return role;
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getYearWinner() {
		return userRepository.findById(ticketRepository.findRandomUser()).orElse(null) ;
	}


	@Override
	public Long getUserIdByUsername(String username){
		User user = userRepository.findByUsername(username); // Assuming you have a method in your repository to find a user by their username
		if (user != null) {
			return user.getUser_id();
		} else {
			// Handle the case where the user is not found
			return null; // or throw an exception
		}
	}

	@Override
	public List<UserDTO> getAllUsers() {
		return userRepository.findAll().stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
	}

	public Boolean deleteUser(Long userId){
		User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("user with ID : " + userId + " cannot be found"));
		userRepository.delete(user);
		return true;
	}


	public User findUserById(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("user with ID : " + userId + " cannot be found"));
	}

	@Override
	public Collection<UserDTO> findSimpleUsers(ROLES roles) {
		return userRepository.findByRole(ROLES.USER).stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
	}
}
