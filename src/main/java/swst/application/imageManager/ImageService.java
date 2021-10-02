package swst.application.imageManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import swst.application.exceptionhandlers.ExceptionDetails;
import swst.application.exceptionhandlers.ExceptionDetails.EXCEPTION_CODES;
import swst.application.exceptionhandlers.ExceptionFoundation;

@PropertySource("userdefined.properties")
public class ImageService implements FileStorageServices {
	@Value("${application.folder.product-images}")
	private String destination;

	@Value("${application.folder.product-images}")
	private String productImageDestination;

	@Value("${application.folder.profile-images}")
	private String profileImageDestination;

	@Value("${application.folder.preview-image}")
	private String previewImageDestination;

	public Path directory = Paths.get(destination);
	public Path productImage = Paths.get(destination + "/" + productImageDestination);
	public Path profileImage = Paths.get(destination + "/" + profileImageDestination);
	public Path previewImage = Paths.get(destination + "/" + previewImageDestination);

	@Override
	@PostConstruct
	public void init() {
		try {
			if (Files.isDirectory(directory) == false) {
				Files.createDirectory(directory);
				System.out.println("Directory " + destination + " Created!");
			}
			if (Files.isDirectory(productImage) == false) {
				Files.createDirectory(productImage);
				System.out.println("Directory " + productImageDestination + " Created!");
			}
			if (Files.isDirectory(profileImage) == false) {
				Files.createDirectory(profileImage);
				System.out.println("Directory " + profileImageDestination + " Created!");
			}
			if (Files.isDirectory(previewImage) == false) {
				Files.createDirectory(previewImage);
				System.out.println("Directory " + previewImageDestination + " Created!");
			}

		} catch (IOException exc) {
			throw new ExceptionFoundation(EXCEPTION_CODES.CORE_INIT_FAILED,
					"[ FAILED ] Init doesn't failed, it can't create a folder.");
		}
	}

	@Override
	public String fileNameGenerator() {
		// Left 27 Right 122 TargetLength 10
		int targetLength = 10;
		int lowerstAlp = 65;
		int hightAlp = 90;

		Random randomString = new Random();
		StringBuilder buffer = new StringBuilder(targetLength);

		for (int i = 0; i < targetLength; i++) {
			int randomLimitedInt = lowerstAlp + (int) (randomString.nextFloat() * (hightAlp - lowerstAlp + 1));
			buffer.append((char) randomLimitedInt);
		}

		return "product-" + buffer.toString() + "-";
	}

	@Override
	public void save(MultipartFile file, String newCaseName) {
		try {
			Files.copy(file.getInputStream(), this.directory.resolve(newCaseName), StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception exc) {
			throw new RuntimeException(
					"[ FAILED ] Error occures when trying to store this file. Error: " + exc.getMessage());
		}

	}

	@Override
	public void delete(String fileToDelete) {
		try {
			Files.delete(this.directory.resolve(fileToDelete));
			System.out.println("A user deleted an image.");
		} catch (Exception exc) {
			throw new RuntimeException(
					"[ FAILED ] Can't remove this file. Might be cursed or already not in here. Error: "
							+ exc.getMessage());
		}
	}

	@Override
	public Resource load(String filename) {
		try {
			Path file = directory.resolve(filename);
			Resource reso = new UrlResource(file.toUri());
			if (reso.exists() || reso.isReadable()) {
				return reso;
			} else {
				throw new ExceptionFoundation(ExceptionDetails.EXCEPTION_CODES.SEARCH_CAN_NOT_READ,
						"[ FAILED ]  Can not recieve file. Error: Can't read");
			}
		} catch (MalformedURLException exc) {
			throw new RuntimeException(
					"[ FAILED ] MalformedURLException. Can not recieve file. Error: " + exc.getMessage());
		}
	}

	@Override
	public Stream<Path> loadAll() throws ExceptionFoundation {
		throw new ExceptionFoundation(ExceptionDetails.EXCEPTION_CODES.FEATURE_NOT_IMPLEMENTED,
				"[ NOT IMPLEMENTED ] This feature is currently unavailable.");
	}

	@Override
	public void purgeFiles() throws ExceptionFoundation {
		throw new ExceptionFoundation(ExceptionDetails.EXCEPTION_CODES.FEATURE_TOO_DANGEROUS_TO_HAVE_IT,
				"[ NOT ALLOWED ] This feature will delete everything. But it is here just for testing.");
		// FileSystemUtils.deleteRecursively(directory.toFile());

	}

}
