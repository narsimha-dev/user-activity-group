package com.user.social.communicate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
        System.err.println("Error String is called");
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
        System.err.println("Error String is called: "+message);

    }

}
