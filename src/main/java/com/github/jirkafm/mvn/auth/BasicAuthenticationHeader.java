package com.github.jirkafm.mvn.auth;

import java.util.Base64;
import java.util.Objects;

public class BasicAuthenticationHeader implements AuthenticationHeader {

	private final String username;
	private final String password;

	public BasicAuthenticationHeader(final String username, final String password) {
		this.username = Objects.requireNonNull(username);
		this.password = Objects.requireNonNull(password);
	}

	@Override
	public String headerKey() {
		return "Authorization";
	}

	@Override
	public String headerValue() {
		return String.format("Basic %s", encodeCredentials());
	}

	private String encodeCredentials() {
		final String data = String.join(":", username, password);
		return Base64.getEncoder().encodeToString(data.getBytes());
	}

}
