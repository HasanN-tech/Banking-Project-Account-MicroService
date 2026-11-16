package com.hasan.exception;

public class ServiceUnavailableException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8498582208117580314L;

	public ServiceUnavailableException(String msg) {
		super(msg);
	}
	
}
