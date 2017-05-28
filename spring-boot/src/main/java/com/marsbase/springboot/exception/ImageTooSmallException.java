package com.marsbase.springboot.exception;

public class ImageTooSmallException extends Exception {

	private static final long serialVersionUID = 1L;

	public ImageTooSmallException(String message) {
		super(message);
	}
}
