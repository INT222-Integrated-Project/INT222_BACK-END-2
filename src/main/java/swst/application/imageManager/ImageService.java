package swst.application.imageManager;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public class ImageService implements FileStorageServices {
	
	//This will create a folder to store an images file if not existed.
	@Override
	//PostConstrict annotation tells the application to run this method before finishing the build process.
	@PostConstruct
	public void init() {
		/*try {
			System.out.println("");
		} catch (IOException exc) {
			throw new RuntimeException("");
		}*/
	}

	@Override
	public String fileNameGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(MultipartFile file, String newCaseName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String fileToDelete) {
		// TODO Auto-generated method stub

	}

	@Override
	public Resource load(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stream<Path> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void purgeFiles() {
		// TODO Auto-generated method stub

	}

}
