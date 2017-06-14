package com.marsbase.springboot.test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.marsbase.springboot.App;
import com.marsbase.springboot.controller.ProfileController;
import com.marsbase.springboot.model.entity.Interest;
import com.marsbase.springboot.model.entity.Profile;
import com.marsbase.springboot.model.entity.SiteUser;
import com.marsbase.springboot.service.InterestService;
import com.marsbase.springboot.service.ProfileService;
import com.marsbase.springboot.service.UserService;

@Transactional
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class ProfileControllerRestTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private UserService userService;

	@Autowired
	private InterestService interestService;

	@Autowired
	private ProfileController profileController;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	/**
	 * create user
	 * 
	 */
	private void createTestUser(String username, String firstname, String lastname) {
		SiteUser user = new SiteUser(username, "123456", firstname, lastname);
		user.setFirstName("test");
		user.setLastName("test");
		userService.save(user);

		// create new profile
		profileController.showProfile();
	}

	@Test
	@WithMockUser(username = "test!@gmail.com")
	public void testSaveAndDeleteInterest() throws Exception {

		createTestUser("test!@gmail.com", "tom", "zac");

		String interestText = "thisisinterest";
		mockMvc.perform(post("/save-interest").param("name", interestText)).andExpect(status().isOk());

		Interest interest = interestService.getInterest(interestText);

		assertNotNull("interest should not be null", interest);
		assertEquals("retrieve interest should be the same", new Interest(interestText), interest);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SiteUser user = userService.getUser(authentication.getName());

		assertNotNull("user shold not be null", user);

		Profile profile = profileService.getUserProfile(user);

		assertNotNull(profile);
		assertNotNull(profile.getInterests());
		assertTrue("should contain interest", profile.getInterests().contains(interest));

		mockMvc.perform(post("/delete-interest").param("name", interestText)).andExpect(status().isOk());

		Profile profile2 = profileService.getUserProfile(user);

		assertFalse("should contain interest", profile2.getInterests().contains(interest));
	}

}
