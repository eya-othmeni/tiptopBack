package com.tiptop.users.controller;


import java.util.Collection;
import java.util.List;

import com.tiptop.users.config.JwtTokenUtil;
import com.tiptop.users.dto.UserDTO;
import com.tiptop.users.entities.ROLES;
import com.tiptop.users.model.JwtRequest;
import com.tiptop.users.model.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.tiptop.users.entities.User;
import com.tiptop.users.service.UserService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

//    @Autowired
//    UserService userService;

//    @RequestMapping(path="all",method=RequestMethod.GET)
//    public List<User> getAllUsers() {
//        return userService.findAllUsers();
//    }
//
    @GetMapping
    public Collection<UserDTO> getAllUsersv1(){
        return userService.getAllUsers();
    }
    @GetMapping("/simple")
    public Collection<UserDTO> getNonAdminUsers(){
        return userService.findSimpleUsers(ROLES.USER);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/users/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }

    @PostMapping("/add")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/getYearWinner")
    public User getYearWinner(){
        return userService.getYearWinner();
    }

    @GetMapping("/getUserId/{username}")
    public Long getUserIdByUsername(@PathVariable String username){
        return userService.getUserIdByUsername(username);
    }
/*
	    @DeleteMapping("/users/{username}")
	    public void deleteUser(@PathVariable String username) {
	        userService.deleteUserByUsername(username);
	    }

	    @GetMapping("/users/{username}/roles")
	    public List<Role> getUserRoles(@PathVariable String username) {
	        return userService.findUserRolesByUsername(username);
	    }

	    @PutMapping("/users/{username}")
	    public User updateUser(@RequestBody User user, @PathVariable String username) {
	        return userService.updateUser(user, username);
	    }
	    */

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }





    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }





}



