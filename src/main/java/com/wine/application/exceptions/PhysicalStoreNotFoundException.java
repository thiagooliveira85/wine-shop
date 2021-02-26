package com.wine.application.exceptions;

public class PhysicalStoreNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PhysicalStoreNotFoundException(String message) {
		super(message);
	}

}
