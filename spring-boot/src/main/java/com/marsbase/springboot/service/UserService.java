package com.marsbase.springboot.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper;
import org.springframework.stereotype.Service;

import com.marsbase.springboot.dao.UserDao;
import com.marsbase.springboot.dao.VerificationDao;
import com.marsbase.springboot.model.SiteUser;
import com.marsbase.springboot.model.TokenType;
import com.marsbase.springboot.model.VerificationToken;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private VerificationDao verificationDao;

	// @Autowired
	// private PasswordEncoder passwordEncoder;

	public void register(SiteUser siteUser) {
		siteUser.setRole("ROLE_USER");
		userDao.save(siteUser);
	}

	public void save(SiteUser user) {
		userDao.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		SiteUser siteUser = userDao.findByEmail(email);

		if (siteUser == null) {
			return null;
		}

		List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(siteUser.getRole());
		String password = siteUser.getPassword();
		boolean enabled = siteUser.isEnabled();

		return new User(email, password, enabled, true, true, true, auth);
	}

	public String createEmailVerficationToken(SiteUser user) {
		VerificationToken token = new VerificationToken(UUID.randomUUID().toString(), user, TokenType.REGISTRATION);
		verificationDao.save(token);

		return token.getToken();
	}

	public VerificationToken getVerificationToken(String token) {
		return verificationDao.findByToken(token);
	}

}
