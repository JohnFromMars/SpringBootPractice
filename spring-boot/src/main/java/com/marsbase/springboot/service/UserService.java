package com.marsbase.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.marsbase.springboot.dao.UserDao;
import com.marsbase.springboot.model.SiteUser;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	public void register(SiteUser siteUser) {
		userDao.save(siteUser);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		SiteUser siteUser = userDao.findByEmail(email);

		if (siteUser == null) {
			return null;
		}

		List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
		String password = siteUser.getPassword();
		
		return new User(email, password, auth);
	}

}
