package com.marsbase.springboot.status;

public class PhotoUploadStatus {

	private String message;

	public PhotoUploadStatus() {
	}

	public PhotoUploadStatus(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
