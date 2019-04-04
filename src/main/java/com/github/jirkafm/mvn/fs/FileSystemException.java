package com.github.jirkafm.mvn.fs;

public class FileSystemException extends RuntimeException {

	private static final long serialVersionUID = 7568016840703246857L;

	public FileSystemException() {
		super();
	}

	public FileSystemException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public FileSystemException(final Throwable cause) {
		super(cause);
	}

}
