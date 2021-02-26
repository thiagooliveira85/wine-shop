package com.wine.application.exceptions;

public class ZipCodeRangeNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ZipCodeRangeNotFoundException(String message) {
		super(message);
	}

}
