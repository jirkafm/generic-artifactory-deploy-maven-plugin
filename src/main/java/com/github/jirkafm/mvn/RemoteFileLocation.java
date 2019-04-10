package com.github.jirkafm.mvn;

import java.net.URI;
import java.util.Objects;

class RemoteFileLocation {

	private final URI uri;
	private final String fileName;

	public RemoteFileLocation(final URI uri, final String fileName) {
		this.uri = Objects.requireNonNull(uri);
		this.fileName = Objects.requireNonNull(fileName);
	}

	public String getLocation() {
		final String uriString = uri.toString();
		final StringBuilder locationBuilder = new StringBuilder(uriString);
		if (!uriString.endsWith("/")) {
			locationBuilder.append("/");
		}
		locationBuilder.append(fileName);
		return locationBuilder.toString();

	}

}
