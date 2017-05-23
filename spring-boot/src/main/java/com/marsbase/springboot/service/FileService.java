package com.marsbase.springboot.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;
import com.marsbase.springboot.exception.InvalidFileException;

@Service
public class FileService {
	private static final int RANDOM_BOUND = 1000;

	@Value("${photo.file.extensions}")
	private String photoExtensions;
	private Random random = new Random();

	private String getFileExtension(String fileName) {

		int doPosition = fileName.lastIndexOf(".");

		if (doPosition < 0) {
			return null;

		} else {
			return fileName.substring(doPosition + 1).toLowerCase();
		}

	}

	private boolean isImageExtension(String fileExtension) {

		String testExtension = fileExtension.toLowerCase().trim();

		for (String validExtension : photoExtensions.split(",")) {
			if (validExtension.equals(testExtension)) {
				return true;
			}
		}

		return false;
	}

	private File makeSubdirectory(String baseDirectory, String prefix) {
		int nDirectory = random.nextInt(RANDOM_BOUND);
		String sDirectoy = String.format("%s%03d", prefix, nDirectory);
		File directory = new File(baseDirectory, sDirectoy);

		if (!directory.exists()) {
			directory.mkdirs();
		}

		return directory;
	}

	public void saveImageFile(MultipartFile file, String baseDirectory, String subDirctoryPrefix, String filePrefix)
			throws InvalidFileException, IOException {

		int nFileName = random.nextInt(RANDOM_BOUND);
		String fileName = String.format("%s%03d", filePrefix, nFileName);
		String extension = getFileExtension(fileName);

		if (extension == null) {
			throw new InvalidFileException("No extension.");
		}

		if (isImageExtension(extension) == false) {
			throw new InvalidFileException("Not an image extension.");
		}

		File subDirectory = makeSubdirectory(baseDirectory, subDirctoryPrefix);
		Path filePath = Paths.get(subDirectory.getCanonicalPath(), fileName + "." + extension);

		// delete if file already exist
		java.nio.file.Files.deleteIfExists(filePath);

		//copy file
		java.nio.file.Files.copy(file.getInputStream(), filePath);
	}

}
