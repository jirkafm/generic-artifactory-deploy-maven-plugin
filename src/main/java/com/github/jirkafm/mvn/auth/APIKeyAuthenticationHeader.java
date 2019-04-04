package com.github.jirkafm.mvn.auth;

import java.util.Objects;

public class APIKeyAuthenticationHeader implements AuthenticationHeader {

	private final String apiKey;

	public APIKeyAuthenticationHeader(final String apiKey) {
		this.apiKey = Objects.requireNonNull(apiKey);
	}

	@Override
	public String headerKey() {
		return "X-JFrog-Art-Api";
	}

	@Override
	public String headerValue() {
		return apiKey;
	}

}
