package com.marsbase.springboot.test;

import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.marsbase.springboot.App;
import com.marsbase.springboot.service.FileService;

@Transactional
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class FileServiceTest {

	@Autowired
	private FileService fileService;

	@Value("${photo.upload.directory}")
	private String photoUploadDirectory;

	@Test
	public void testExtension() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		Method method = FileService.class.getDeclaredMethod("getFileExtension", String.class);

		method.setAccessible(true);

		assertEquals("png", (String) method.invoke(fileService, "test.png"));
		assertEquals("jpg", (String) method.invoke(fileService, "s.jpg"));
		assertEquals("jpeg", (String) method.invoke(fileService, "olllla.jpeg"));
		assertEquals("gif", (String) method.invoke(fileService, "test.gif"));
		assertNull((String) method.invoke(fileService, "test"));

	}

	@Test
	public void testIsImageExtension() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		Method method = FileService.class.getDeclaredMethod("isImageExtension", String.class);

		method.setAccessible(true);

		// should be true
		assertTrue((Boolean) method.invoke(fileService, "png"));
		assertTrue((Boolean) method.invoke(fileService, "jpg"));
		assertTrue((Boolean) method.invoke(fileService, "jpeg"));
		assertTrue((Boolean) method.invoke(fileService, "gif"));

		assertTrue((Boolean) method.invoke(fileService, "PNG"));
		assertTrue((Boolean) method.invoke(fileService, "JPG"));
		assertTrue((Boolean) method.invoke(fileService, "JPEG"));
		assertTrue((Boolean) method.invoke(fileService, "GIF"));

		assertTrue((Boolean) method.invoke(fileService, "png "));
		assertTrue((Boolean) method.invoke(fileService, " jpg"));
		assertTrue((Boolean) method.invoke(fileService, " jpeg "));
		assertTrue((Boolean) method.invoke(fileService, "gif     "));

		// should be false
		assertFalse((Boolean) method.invoke(fileService, "doc"));
		assertFalse((Boolean) method.invoke(fileService, ""));
	}

	@Test
	public void testMakeSubdirectory() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = FileService.class.getDeclaredMethod("makeSubdirectory", String.class, String.class);
		method.setAccessible(true);

		for (int i = 0; i < 10000; i++) {
			File file = (File) method.invoke(fileService, photoUploadDirectory, "photo");
			assertTrue("File should exist : " + file.getAbsolutePath(), file.exists());
		}

	}
}
