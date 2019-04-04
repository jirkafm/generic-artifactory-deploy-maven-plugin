package com.github.jirkafm.mvn.deploy;

import java.io.File;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

public class ArtifactoryFileDeployInput {

	private final File file;
	private final String httpMethod;
	private final MediaType requestType;

	public ArtifactoryFileDeployInput(final File file, final String httpMethod, final MediaType requestType) {
		this.file = file;
		this.httpMethod = httpMethod;
		this.requestType = requestType;
	}

	public Entity<File> createFileEntity() {
		return Entity.entity(file, requestType);
	}

	public String getHttpMethod() {
		return httpMethod;
	}

}
