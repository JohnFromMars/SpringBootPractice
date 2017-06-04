package com.marsbase.springboot.test;

import java.util.HashSet;

import static org.junit.Assert.*;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.marsbase.springboot.App;
import com.marsbase.springboot.model.Interest;
import com.marsbase.springboot.model.Profile;
import com.marsbase.springboot.model.SiteUser;
import com.marsbase.springboot.service.InterestService;
import com.marsbase.springboot.service.ProfileService;
import com.marsbase.springboot.service.UserService;

@Transactional
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class PeofileTest {

	@Autowired
	private ProfileService profileService;

	@Autowired
	private UserService userService;

	@Autowired
	private InterestService interestService;

	private SiteUser[] users = { new SiteUser("ohohyeah@gmail.com", "hellothere","zac","wide"),
			new SiteUser("nono@gmail.com", "asdfg","tom","paddy"), new SiteUser("kkkk@gmail.com", "098764321","anominous","zic") };

	private String[][] interests = { { "music", "guitarxxxx", "band" }, { "music", "music", "coding", "footbal" },
			{ "movie", "metal", "animate", "basketball" }, };

	@Test
	public void testInterest() {

		for (int i = 0; i < users.length; i++) {
			SiteUser siteUser = users[i];
			String[] interestArray = interests[i];

			userService.register(siteUser);

			HashSet<Interest> interestSet = new HashSet<>();

			for (String s : interestArray) {
				Interest interest = interestService.createIfNotExist(s);
				interestSet.add(interest);

				assertNotNull("interest should not be null", interest);
				assertNotNull("interest should has id", interest.getId());
				assertEquals("text should match", s.toLowerCase(), interest.getName().toLowerCase());

			}

			Profile profile = new Profile(siteUser);
			profile.setInterests(interestSet);
			profileService.save(profile);

			Profile retrieveProfile = profileService.getUserProfile(siteUser);

			assertNotNull("profile should nt be null", retrieveProfile);
//			assertEquals("user should be the same", siteUser, retrieveProfile.getUser());
			assertEquals("interests should be the same", interestSet, retrieveProfile.getInterests());

		}

	}

}
