package swst.application.imageManager;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageServices {
	public void init();
	
	//This will generate a file name before storing in the file.
	public String fileNameGenerator();
	
	//This will save a file.
	public void save(MultipartFile file, String newCaseName);
	
	//This will delete a file.
	public void delete(String fileToDelete);

	//This will load and send a file.
	public Resource load(String filename);
	
	/*
	 * D A N G E R   Z O N E
	 */
	//This will load everything.
	public Stream<Path> loadAll();

	// This will nuke your files.
	public void purgeFiles();

}
