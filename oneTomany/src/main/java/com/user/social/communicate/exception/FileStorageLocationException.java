package com.user.social.communicate.exception;

import java.io.IOException;

public class FileStorageLocationException extends RuntimeException {

    public FileStorageLocationException(String message){
        super(message);
    }
    public FileStorageLocationException(String message, Throwable error) {
        super(message,error);
    }
}
