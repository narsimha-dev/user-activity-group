package com.user.social.communicate.controller;
import com.user.social.communicate.file.UploadFileResponse;
import com.user.social.communicate.service.FileStorageServiceImpl;

//import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageServiceImpl fileStorageService;

   @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file")MultipartFile multipartFile){

       String fileName= fileStorageService.storagFile(multipartFile);
       String fileDownloadUri= ServletUriComponentsBuilder.fromCurrentContextPath()
                                       .path("/downloadFiles")
                                       .path(fileName)
                                        .toUriString();
             return  new UploadFileResponse(fileName,fileDownloadUri,multipartFile.getContentType(),multipartFile.getSize());
   }
@PostMapping("/uploadFiles")
    public List multipleUploadFiles(@RequestParam("file") MultipartFile[] multipartFiles){
         List multipleFiles= Arrays.stream(multipartFiles)
                                    .map(this::uploadFile)
                                    .collect(Collectors.toList());
         System.out.println(Arrays.toString(multipartFiles));
       return  multipleFiles;
}
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
