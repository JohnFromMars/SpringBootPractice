package com.marsbase.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//@formatter:off
		http.authorizeRequests()
		
		    //pages authorization
		    .antMatchers("/","/about").permitAll()
		    
		    //resources authorization
		    .antMatchers(
		    		"/js/*",
		    		"/css/*",
		    		"/img/*"
		    		).permitAll()
		    
		    //other request authorization
		    .anyRequest().authenticated()
		    
		    //login page auth
		    .and().formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll()
		    
		    //logout auth
		    .and().logout().permitAll();

		
		//@formatter:on
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		//@formatter:off
		auth.inMemoryAuthentication()
		    .withUser("mars").password("mars").roles("USER");
		
		//@formatter:on

	}

}
