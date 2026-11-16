package com.hasan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(BusinessException.class)
	public String businessException(Exception ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public String resourceException(Exception ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(ServiceUnavailableException.class)
	public ResponseEntity<String> serviceException(ServiceUnavailableException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception ex) {
		return ex.getMessage();
	}
}
