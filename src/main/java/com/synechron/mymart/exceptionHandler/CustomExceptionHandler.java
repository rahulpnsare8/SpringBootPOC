package com.synechron.mymart.exceptionHandler;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.synechron.mymart")
public class CustomExceptionHandler {
	
	@ExceptionHandler(value = {NullPointerException.class})
	public ResponseEntity<String> handleNullPointerException(){
		return new ResponseEntity<String>("Error occured due to null object", HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(value = {NoSuchElementException.class})
	public ResponseEntity<String> handleNoSuchElementException(){
		return new ResponseEntity<String>("No record found in database", HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(value={IOException.class})
	public ResponseEntity<String> handleIOException(){
		 return new ResponseEntity<String>("Error occured while reading file", HttpStatus.EXPECTATION_FAILED);
	}

}
