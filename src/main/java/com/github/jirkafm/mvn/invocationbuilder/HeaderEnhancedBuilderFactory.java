package com.github.jirkafm.mvn.invocationbuilder;

import java.io.File;

import javax.ws.rs.client.Invocation.Builder;

import com.github.jirkafm.mvn.deploy.ArtifactoryHeaders;

public class HeaderEnhancedBuilderFactory implements BuilderFactory {

	private final BuilderFactory builderFactory;
	private final ArtifactoryHeaders artifactoryHeaders;

	public HeaderEnhancedBuilderFactory(final BuilderFactory builderFactory,
			final ArtifactoryHeaders artifactoryHeaders) {
		this.builderFactory = builderFactory;
		this.artifactoryHeaders = artifactoryHeaders;
	}

	@Override
	public Builder getInstance(final File file) {
		final Builder builder = builderFactory.getInstance(file);
		artifactoryHeaders.getHeaders(file).forEach(builder::header);
		return builder;
	}

}
