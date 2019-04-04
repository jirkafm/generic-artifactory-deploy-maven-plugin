package com.github.jirkafm.mvn.invocationbuilder;

import java.io.File;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class WebTargetBuilderFactory implements BuilderFactory {

	private final WebTarget target;
	private final MediaType responseType;

	public WebTargetBuilderFactory(final WebTarget target, final MediaType responseType) {
		this.target = target;
		this.responseType = responseType;
	}

	@Override
	public Builder getInstance(final File file) {
		final WebTarget fileWebTarget = target.path(file.getName());
		return fileWebTarget.request(responseType);
	}

}
