package com.hasan.exception;

@SuppressWarnings("serial")
public class BusinessException extends RuntimeException{

	public BusinessException(String msg) {
		super(msg);
	}
}
