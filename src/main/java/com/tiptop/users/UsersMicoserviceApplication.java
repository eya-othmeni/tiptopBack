package com.tiptop.users;

import com.tiptop.users.entities.ROLES;
import com.tiptop.users.entities.User;
import com.tiptop.users.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class UsersMicoserviceApplication {
	@Autowired
	UserServiceImpl userService;


	public static void main(String[] args) {
		SpringApplication.run(UsersMicoserviceApplication.class, args);
	}
	@PostConstruct
	void init_users() {
		//admin
		User admin = new User();
		admin.setRole(ROLES.USER);
		admin.setUsername("admin");
		admin.setPassword("admin");
		admin.setEmail("admin@gmail.com");
		admin.setEnabled(true);
		userService.saveUser(admin);


		//utilisateur
		User utilisateur = new User();
		utilisateur.setRole(ROLES.USER);
		utilisateur.setUsername("utilisateur");
		utilisateur.setPassword("12345");
		utilisateur.setEmail("utilisateur@gmail.com");
		utilisateur.setEnabled(true);
		userService.saveUser(utilisateur);

		//daniel
		User daniel = new User();
		daniel.setRole(ROLES.USER);
		daniel.setUsername("daniel");
		daniel.setPassword("12345");
		daniel.setEmail("daniel@gmail.com");
		daniel.setEnabled(true);
		userService.saveUser(daniel);
		}
	@Bean
	BCryptPasswordEncoder getBCE() {
		return new BCryptPasswordEncoder();
	}
	
	

}
