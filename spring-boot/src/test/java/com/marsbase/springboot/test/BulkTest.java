package com.marsbase.springboot.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.marsbase.springboot.App;

@Transactional
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class BulkTest {
	private static final String NAME_FILE = "/com/marsbase/springboot/test/data/names.txt";
	private static final String INTEREST_FILE = "/com/marsbase/springboot/test/data/hobbies.txt";

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
		List<String> interests = loadFile(INTEREST_FILE, 25);
		List<String> names = loadFile(NAME_FILE, 25);
		
		for(String s:names){
			System.out.println(s);
		}
	}

}
