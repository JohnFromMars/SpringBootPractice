package com.marsbase.springboot.service;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.marsbase.springboot.exception.ImageTooSmallException;
import com.marsbase.springboot.exception.InvalidFileException;
import com.marsbase.springboot.model.FileInfo;

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

	@PreAuthorize("isAuthenticated()")
	public FileInfo saveImageFile(MultipartFile file, String baseDirectory, String subDirctoryPrefix, String filePrefix,
			int width, int height) throws InvalidFileException, IOException, ImageTooSmallException {

		int nFileName = random.nextInt(RANDOM_BOUND);
		String fileName = String.format("%s%03d", filePrefix, nFileName);
		String extension = getFileExtension(file.getOriginalFilename());

		if (extension == null) {
			throw new InvalidFileException("No extension.");
		}

		if (isImageExtension(extension) == false) {
			throw new InvalidFileException("Not an image extension.");
		}

		File subDirectory = makeSubdirectory(baseDirectory, subDirctoryPrefix);
		Path filePath = Paths.get(subDirectory.getCanonicalPath(), fileName + "." + extension);

		BufferedImage resizeImage = resizeImage(file, width, height);

		// copy file
		ImageIO.write(resizeImage, extension, filePath.toFile());

		return new FileInfo(fileName, extension, subDirectory.getName(), baseDirectory);
	}

	private BufferedImage resizeImage(MultipartFile inputFile, int width, int height)
			throws IOException, ImageTooSmallException {

		BufferedImage image = ImageIO.read(inputFile.getInputStream());

		if (image.getHeight() < height || image.getWidth() < width) {
			throw new ImageTooSmallException("This photo is too small");
		}

		double widthScale = (double) width / image.getWidth();
		double heightScale = (double) height / image.getHeight();
		double scale = Math.max(widthScale, heightScale);

		BufferedImage scaleImage = new BufferedImage((int) (scale * image.getWidth()), (int) (scale * image.getHeight()), image.getType());

		Graphics2D graphics2d = scaleImage.createGraphics();

		AffineTransform affineTransform = AffineTransform.getScaleInstance(scale, scale);

		graphics2d.drawImage(image, affineTransform, null);

		return scaleImage.getSubimage(0, 0, width, height);
	}

}
