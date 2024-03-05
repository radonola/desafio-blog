package br.com.raissa.blog.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ComentarioException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String details;
	private HttpStatus httpStatus;
	
	public ComentarioException(String message, String title) {
		super(message);
		this.title = title;
	}
	
	public ComentarioException(String message, String title, HttpStatus httpStatus) {
		super(message);
		this.title = title;
		this.httpStatus = httpStatus;
	}
	
	public ComentarioException(String message, String title, String details, HttpStatus httpStatus) {
		super(message);
		this.title = title;
		this.details = details;
		this.httpStatus = httpStatus;
	}
	
}
