package com.wine.infrastructure.exceptions.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.wine.application.exceptions.PhysicalStoreNotFoundException;
import com.wine.application.exceptions.ZipCodeRangeNotFoundException;
import com.wine.domain.exceptions.BusinessException;
import com.wine.domain.exceptions.RangeNotAllowedException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(PhysicalStoreNotFoundException.class)
	public ResponseEntity<StandardError> physicalStoreNotFoundException(PhysicalStoreNotFoundException e, HttpServletRequest request) {
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(ZipCodeRangeNotFoundException.class)
	public ResponseEntity<StandardError> zipCodeRangeNotFoundException(ZipCodeRangeNotFoundException e, HttpServletRequest request) {
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<StandardError> businessException(BusinessException e, HttpServletRequest request) {
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(RangeNotAllowedException.class)
	public ResponseEntity<StandardError> rangeNotAllowedException(RangeNotAllowedException e, HttpServletRequest request) {
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<StandardError> illegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
}
