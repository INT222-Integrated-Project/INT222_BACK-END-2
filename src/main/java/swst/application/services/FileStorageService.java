package swst.application.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import swst.application.errorsHandlers.ExceptionFoundation;
import swst.application.errorsHandlers.ExceptionresponsesModel.EXCEPTION_CODES;

@Service
@Slf4j
@PropertySource("userdefined.properties")
public class FileStorageService {

	private final String methods = "[ POST CONSTRUCT ]";
	private final String mainFolder = "application/images/";
	// DO NOT CHANGE THIS VALUE.
	private final String[] folderList = new String[] { "products", "profiles", "models", "product-colors" };

	@PostConstruct
	public void init() {
		log.info(methods + " Entering post construct methods.");
		createDirectory();
		log.info(LocalDate.now() + "");
	}

	public void createDirectory() {

		log.info("=========DIRECTORY==========================");
		for (int i = 0; i < folderList.length; i++) {
			log.info(methods + " Building directory :" + mainFolder + folderList[i]);
			try {
				Path directory = Paths.get(mainFolder + folderList[i]);
				if (Files.isDirectory(directory) == false) {
					Files.createDirectories(directory);
					log.info("[ POST CONSTRUCT ] Created");
				} else {
					log.info("[ POST CONSTRUCT ] Already Existed, leave as it is.");
				}
			} catch (IOException exc) {
				throw new ExceptionFoundation(EXCEPTION_CODES.CORE_INIT_FAILED,
						"[ FAILED ] Can't check or create directory.");
			}
		}
		log.info("=============================================");
	}

	public String saveImage(MultipartFile imageFile, String folder) {

		if (imageFile.getSize() > 8000000) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SAVE_IMAGE_FAILED, "[ FAILED ] Image can't be more than 8MB");
		}

		int alLength = 10;
		int aal = 65;
		int zal = 90;

		Random randomString = new Random();
		StringBuilder buffer = new StringBuilder(alLength);

		for (int i = 0; i < alLength; i++) {
			int ramdomLimitedInt = aal + (int) (randomString.nextFloat() * (zal - aal + 1));
			buffer.append((char) ramdomLimitedInt);
		}

		String imageName = LocalDate.now() + buffer.toString() + imageFile.getOriginalFilename().substring(imageFile.getOriginalFilename().length() - 4);

		try {
			Files.copy(imageFile.getInputStream(), Paths.get(mainFolder + folder).resolve(imageName),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception exc) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SAVE_IMAGE_FAILED, "[ FAILED ] " + exc.getMessage());
		}

		return imageName;
	}

	public void deleteImage(String fileName, String folder) {
		try {
			Files.delete(Paths.get(mainFolder + folder).resolve(fileName));
			log.info("[ DELETED ] " + fileName);
		} catch (Exception exc) {
			log.info("[ Not found but nothing to worry. ] " + fileName);
		}
	}

	public Resource loadImage(String fileName, String folder) {
		try {
			Path directory = Paths.get(mainFolder + folder);
			Path file = directory.resolve(fileName);
			Resource target = new UrlResource(file.toUri());
			log.info(folder + " | " + fileName + " | " + target);
			 
			if (target.exists() && target.isReadable()) {
				return target;
			} else {
				throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_CAN_NOT_READ,
						"[ FAILED ] Might not be readable or not exist.");
			}
		} catch (MalformedURLException exc) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_CAN_NOT_READ, "[ FAILED ] MalformedURLException.");
		}
	}

}
