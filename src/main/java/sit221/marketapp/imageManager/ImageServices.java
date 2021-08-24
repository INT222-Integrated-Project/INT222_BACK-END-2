package sit221.marketapp.imageManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@PropertySource("classpath:application.properties")
@Service
public class ImageServices implements FileStorageService {

	@Value("${marketapp.imagepath}")
	private String destination = "";

	public Path directory = Paths.get("product-images");

	@Override
	@PostConstruct
	public void init() {
		try {
			System.out.println("[ ENTERED ] Entering file init..");
			if (Files.isDirectory(directory) == false) {
				Files.createDirectory(directory);
				System.out.println("[ OK! ] Directory \"" + destination + "\" Created.");
			} else {
				System.out.println("[ NOPE ] Directory \"" + destination + "\" already exist.");
			}
		} catch (IOException exc) {
			throw new RuntimeException("[ FAILED ] Init doesn't failed, can't create a folder.");
		}
	}

	// NEXT : Make image name random and store its name into the database.
	@Override
	public void save(MultipartFile imageFile, String newCaseName) {
		try {
			Files.copy(imageFile.getInputStream(), this.directory.resolve(newCaseName),
					StandardCopyOption.REPLACE_EXISTING);
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
				throw new RuntimeException("[ FAILED ]  Can not recieve file. Error: Can't read");
			}
		} catch (MalformedURLException exc) {
			throw new RuntimeException(
					"[ FAILED ] MalformedURLException. Can not recieve file. Error: " + exc.getMessage());
		}
	}

	// DANGER ZONE
	@Override
	public void purgeFiles() {
		FileSystemUtils.deleteRecursively(directory.toFile());
	}

	//Not implemented
	@Override
	public Stream<Path> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
