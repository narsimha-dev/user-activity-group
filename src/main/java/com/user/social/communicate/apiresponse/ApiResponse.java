package com.user.social.communicate.apiresponse;

import org.springframework.http.HttpStatus;

public class ApiResponse {

	private int status;
	private String message;
	private Object result;

	public ApiResponse(HttpStatus ok, String message, Object result) {
		super();
		this.status = ok.value();
		this.message = message;
		this.result = result;
	}

    public ApiResponse(HttpStatus status, String message){
        this.status = status.value();
        this.message = message;
    }
    @Override
	public String toString() {
		return "ApiResponse [statusCode=" + status + ", message=" + message +"]";
	}
}
