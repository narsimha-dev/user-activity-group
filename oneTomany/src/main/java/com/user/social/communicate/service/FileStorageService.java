package com.user.social.communicate.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.user.social.communicate.exception.FileStorageLocationException;
@Service
public interface FileStorageService {
    String storagFile(MultipartFile multipartFile) throws FileStorageLocationException;
    //loadFileAsResource
}
