package com.github.jirkafm.mvn;

import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.slf4j.LoggerFactory;

public class WebTargetBuilder {

	private final URI uri;
	private String repository;
	private Map<String, String> queryParams;

	public WebTargetBuilder(final URI uri) {
		this.uri = uri;
	}

	public WebTargetBuilder setRepository(final String repository) {
		this.repository = repository;
		return this;
	}

	public WebTargetBuilder setQueryParams(final Map<String, String> queryParams) {
		this.queryParams = queryParams;
		return this;
	}

	public WebTarget build() {
		final Client client = ClientBuilder.newClient();
		WebTarget baseTarget = client.target(uri);
		baseTarget.register(new LoggingFilter());

		if (repository != null) {
			LoggerFactory.getLogger(getClass()).debug("Setting repository [{}]", repository);
			baseTarget = baseTarget.path(repository);
		}

		if (queryParams != null) {
			for (final Entry<String, String> entry : queryParams.entrySet()) {
				baseTarget = baseTarget.queryParam(entry.getKey(), entry.getValue());
				LoggerFactory.getLogger(getClass()).debug("Param [{}:{}]", entry.getKey(), entry.getValue());
			}
		}

		return baseTarget;
	}

}
