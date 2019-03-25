package gs.uploading.files.storage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService{

	private final Path rootLocation;
	
	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void store(MultipartFile file) {
		try {
			if(file.isEmpty()) {
				throw new StorageException("fail to store empty file " + file.getOriginalFilename());
			}

			Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
		} catch (IOException e) {
			throw new StorageException("fail to store file " + file.getOriginalFilename(), e);
		}
		
	}
	
	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation))
					.map(path -> this.rootLocation.relativize(path));
		} catch (IOException e) {
			throw new StorageException("fail to read store files", e);
		}
	}

	@Override
	public Path load(String filename) {
		return this.rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		Path file = load(filename);
		try {
			Resource resource = new UrlResource(file.toUri());
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}else {
				throw new StorageFileNotFoundException("can not read file " + filename);
			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("can not read file " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(this.rootLocation.toFile());
	}
	
	@Override
	public void init() {
		try {
			if(Files.notExists(this.rootLocation)) {
				Files.createDirectory(this.rootLocation);
			}
		} catch (IOException e) {
			throw new StorageException("can not init storage", e);
		}
	}
	
}
