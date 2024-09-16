package com.tiptop.users.service;

import com.tiptop.users.dto.UserDTO;
import com.tiptop.users.entities.ROLES;
import com.tiptop.users.entities.Role;
import com.tiptop.users.entities.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
	User saveUser(User user);
	User findUserByUsername (String username);
	Role addRole(Role role);

	List<User> findAllUsers();
	User getYearWinner();

	Long getUserIdByUsername(String username);

	List<UserDTO> getAllUsers();

	public Boolean deleteUser(Long userId);

	public User findUserById(Long userId);

	public Collection<UserDTO> findSimpleUsers(ROLES roles);
}
