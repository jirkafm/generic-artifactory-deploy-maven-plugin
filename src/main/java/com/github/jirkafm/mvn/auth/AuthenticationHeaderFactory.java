package com.github.jirkafm.mvn.auth;

import java.util.Optional;

import org.slf4j.LoggerFactory;

public class AuthenticationHeaderFactory {

	private final String username;
	private final String password;
	private final String accessToken;
	private final String apiKey;

	public AuthenticationHeaderFactory(final String username, final String password, final String accessToken,
			final String apiKey) {
		this.username = Optional.ofNullable(username).orElse("");
		this.password = Optional.ofNullable(password).orElse("");
		this.accessToken = Optional.ofNullable(accessToken).orElse("");
		this.apiKey = Optional.ofNullable(apiKey).orElse("");
	}

	public AuthenticationHeader getInstance() {
		if (!apiKey.isEmpty()) {
			return new APIKeyAuthenticationHeader(apiKey);
		} else if (!accessToken.isEmpty()) {
			return new TokenAuthenticatonHeader(accessToken);
		} else if (!password.isEmpty() && !username.isEmpty()) {
			return new BasicAuthenticationHeader(username, password);
		}

		LoggerFactory.getLogger(getClass()).error("No apiKey, accessToken or username and password provided.");
		throw new AuthenticatonHeaderCreationException("Unable to create authentication header.");
	}

}
