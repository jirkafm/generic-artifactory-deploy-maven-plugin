package com.github.jirkafm.mvn;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

public class RemoteFileLocationTest {

	final String uriString = "http://localhost:8080/artifactory";
	final String fileName = "file1";
	final String expected = uriString + "/" + fileName;

	@Test
	public void testWithSlash() {
		final URI uri = URI.create(uriString + "/");
		RemoteFileLocation remoteFileLocation = new RemoteFileLocation(uri, fileName);
		assertEquals(expected, remoteFileLocation.getLocation());
	}

	@Test
	public void testWithoutSlash() {
		final URI uri = URI.create(uriString);
		RemoteFileLocation remoteFileLocation = new RemoteFileLocation(uri, fileName);
		assertEquals(expected, remoteFileLocation.getLocation());
	}

}
