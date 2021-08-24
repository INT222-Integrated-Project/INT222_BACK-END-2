package sit221.marketapp.imageManager;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
public interface FileStorageService {

	public void init();

	public void save(MultipartFile file, String newCaseName);
	
	public void delete(String fileToDelete);

	public Resource load(String filename);

	public Stream<Path> loadAll();

	// DANGER ZONE
	public void purgeFiles();

}
