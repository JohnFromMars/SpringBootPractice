package com.marsbase.springboot.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.marsbase.springboot.App;
import com.marsbase.springboot.model.Interest;
import com.marsbase.springboot.model.Profile;
import com.marsbase.springboot.model.SiteUser;
import com.marsbase.springboot.service.InterestService;
import com.marsbase.springboot.service.ProfileService;
import com.marsbase.springboot.service.UserService;

//@Transactional
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class BulkTest {
	private static final String NAME_FILE = "/com/marsbase/springboot/test/data/names.txt";
	private static final String INTEREST_FILE = "/com/marsbase/springboot/test/data/hobbies.txt";

	@Autowired
	private ProfileService profileService;

	@Autowired
	private UserService userService;

	@Autowired
	private InterestService interestService;

	private List<String> loadFile(String fileName, int maxLength) throws IOException {

		Path filePath = new ClassPathResource(fileName).getFile().toPath();

		Stream<String> stream = Files.lines(filePath);

		//@formatter:off
		 List<String> list = stream.filter(line->!line.isEmpty())
		                           .map(line->line.trim())
		                           .filter(line->line.length() <= maxLength)
		                           .map(line->line.substring(0, 1).toUpperCase() + line.substring(1).toLowerCase())
		                           .collect(Collectors.toList());
		                           
		
		//@formatter:on

		System.out.println(filePath);
		stream.close();

		return list;
	}

	// @Ignore
	@Test
	public void createTestData() throws IOException {
		Random random = new Random();

		List<String> interests = loadFile(INTEREST_FILE, 25);
		List<String> names = loadFile(NAME_FILE, 25);

		for (int numUser = 0; numUser < 4000; numUser++) {
			String firstname = names.get(random.nextInt(names.size()));
			String lastname = names.get(random.nextInt(names.size()));

			String email = firstname.toLowerCase() + lastname.toLowerCase() + "@example.com";

			SiteUser user = userService.getUser(email);

			if (user != null) {
				continue;
			}

			String password = "pass" + firstname.toLowerCase();
			password = password.substring(0, Math.min(15, password.length()));

			assertTrue(password.length() <= 15);

			SiteUser registerUser = new SiteUser(email, password, firstname, lastname);
			registerUser.setEnabled(random.nextInt(5) != 0);

			userService.register(registerUser);

			Profile profile = new Profile(registerUser);
			int numInterest = random.nextInt(7);

			Set<Interest> interests2 = new HashSet<>();

			for (int i = 0; i < numInterest; i++) {
				String interest = interests.get(random.nextInt(interests.size()));
				Interest interest2 = interestService.createIfNotExist(interest);
				interests2.add(interest2);
			}

			profile.setInterests(interests2);
			profileService.save(profile);
		}

		assertTrue(true);
	}

}
