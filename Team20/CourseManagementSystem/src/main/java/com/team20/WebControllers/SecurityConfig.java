package com.team20.WebControllers;

import com.team20.services.MyUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomSuccessHandler customSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/", "/registerstudent", "/registerprof").permitAll()
				.antMatchers("/admin/**", "/h2-console/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/student/**").access("hasRole('ROLE_STUDENT')")
				.antMatchers("/professor/**").access("hasRole('ROLE_PROFESSOR')")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login").successHandler(customSuccessHandler)
				.permitAll()
				.and()
			.logout()
				.permitAll()
			.and()
			.cors().and().csrf().disable() // This allows POST requests.
			.headers().frameOptions().disable(); // This should fix the db web interface
	}

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) 
	  throws Exception {
		auth.userDetailsService(userDetailsService);
	}
}