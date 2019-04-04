package com.github.jirkafm.mvn.auth;

public class TokenAuthenticatonHeader implements AuthenticationHeader {

	private final String token;

	public TokenAuthenticatonHeader(final String token) {
		this.token = token;
	}

	@Override
	public String headerKey() {
		return "Authorization";
	}

	@Override
	public String headerValue() {
		return String.format("Bearer %s", token);
	}

}
