package com.tiptop.users.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService).
		      passwordEncoder(bCryptPasswordEncoder);
		
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/users/login").permitAll();
		http.authorizeRequests().antMatchers("/login1").permitAll();
		http.authorizeRequests().antMatchers("/add").permitAll();
		http.authorizeRequests().antMatchers("/all").permitAll();
		http.authorizeRequests().antMatchers("/tickets").permitAll();
		http.authorizeRequests().antMatchers("/tickets/me").permitAll();
		http.authorizeRequests().antMatchers("/users/{username}").permitAll();
		http.authorizeRequests().antMatchers("/getTicketByUserId/{userId}").permitAll();
		http.authorizeRequests().antMatchers("/jeux-concours").permitAll();
		http.authorizeRequests().antMatchers("/tickets").permitAll();
		http.authorizeRequests().antMatchers("/addTicket").permitAll();
		http.authorizeRequests().antMatchers("/tickets/getAllTickets").authenticated();
		http.authorizeRequests().antMatchers("/getYearWinner").permitAll();
		http.authorizeRequests().antMatchers("/asignTicket/{userId}").permitAll();
		http.authorizeRequests().antMatchers("/updateTicket/{ticketNumber}").permitAll();
		http.authorizeRequests().antMatchers("/getAllTickets").permitAll();
		http.authorizeRequests().antMatchers("/getUserId/{username}").permitAll();
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(new JWTAuthenticationFilter (authenticationManager())) ;
		http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);



	}
	
	

}
