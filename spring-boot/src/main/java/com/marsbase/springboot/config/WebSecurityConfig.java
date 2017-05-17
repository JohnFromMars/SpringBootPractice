package com.marsbase.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.marsbase.springboot.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//@formatter:off
		http.authorizeRequests()
		
		    //pages authorization
		    .antMatchers(
		    		"/",
		    	    "/about",
		    	    "/register",
		            "/verifyemail",
		            "/confirmregister",
		            "/invaliduser",
		            "/expiredtoken").permitAll()
		    
		    //resources authorization
		    .antMatchers(
		    		"/js/*",
		    		"/css/*",
		    		"/img/*"
		    		).permitAll()
		    
		    //other request authorization
		    //Admin authorization
		    .antMatchers(
		    		"/viewstatus",
		    	    "/addstatus",
		    	    "/deletestatus",
		    	    "/editstatus"
		    		).hasRole("ADMIN")
		    
		    //Deny all the other request
		    .anyRequest().denyAll()
		    
		    //login page auth
		    .and().formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll()
		    
		    //logout auth
		    .and().logout().permitAll();

		
		//@formatter:on
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}

}
