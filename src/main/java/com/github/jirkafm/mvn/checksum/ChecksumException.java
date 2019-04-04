package com.github.jirkafm.mvn.checksum;

import java.io.IOException;

/**
 * Checksum exception is thrown when we are unable to checksum a file
 */
public class ChecksumException extends RuntimeException {

	private static final long serialVersionUID = -7833659530178705543L;

	/**
	 * 
	 * @param e cause of failure
	 */
	public ChecksumException(final IOException e) {
		super(e);
	}

}
