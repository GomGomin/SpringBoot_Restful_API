package com.namhun.hello.preword.info.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService{
    @Value("${C:\\Users/user/Desktop/Spring_study/SpringBoot_Restful_API/preword/src/main/java/com/namhun/hello/preword/info/file}")
    private String uploadPath;

    @Override
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        }catch (IOException e){
            throw new RuntimeException("Could not create upload folder!");
        }
    }

    @Override
    public void store(MultipartFile file){
        try{
            if (file.isEmpty()){
                throw new Exception("ERROR : File is empty.");
            }
            Path root = Paths.get(uploadPath);
            if(!Files.exists(root)){
                init();
            }
            try(InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            }
        }catch (Exception e){
            throw new RuntimeException("Could not store the file. Error:" + e.getMessage());
        }
    }

    @Override
    public Stream<Path> loadAll(){
        try{
            Path root = Paths.get(uploadPath);
            return Files.walk(root, 1).filter(path -> !path.equals(root));
        }catch(IOException e){
            throw new RuntimeException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename){
        return Paths.get(uploadPath).resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename){
        try{
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        }catch (MalformedURLException e){
            throw new RuntimeException("Could not read file: " + filename,e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(uploadPath).toFile());
    }
}
