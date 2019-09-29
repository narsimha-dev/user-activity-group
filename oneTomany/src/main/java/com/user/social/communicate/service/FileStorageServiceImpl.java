package com.user.social.communicate.service;

import com.user.social.communicate.exception.FileNotFoundException;
import com.user.social.communicate.exception.FileStorageLocationException;
import com.user.social.communicate.file.FileStorageProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Component
//@Configuration
//@EnableAutoConfiguration
public class FileStorageServiceImpl {

    private final Path fileLocationStorage;
    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        this.fileLocationStorage = Paths.get(fileStorageProperties.getFileUploader())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileLocationStorage);
        } catch (Exception ex) {
            throw new FileStorageLocationException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    public String storagFile(MultipartFile multipartFile){
      //Normalize file Name
        String storageFileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());

     try {
         // check if the file names contains invalid are not
         if(storageFileName.contains("..")){
             throw new FileStorageLocationException("Sorry! Filename contains invalid path sequence " + storageFileName);
         }
        //copy file th target location path(Replace of existing file as well as)
         Path targetLocation= fileLocationStorage.resolve(storageFileName);
         Files.copy(multipartFile.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
         return  storageFileName;
     }catch (IOException e) {
         System.out.println("error ic occurring" + e);
         throw new FileStorageLocationException("Could not store file " + storageFileName + ". Please try again!", e);
     }
    }
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileLocationStorage.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }

}
