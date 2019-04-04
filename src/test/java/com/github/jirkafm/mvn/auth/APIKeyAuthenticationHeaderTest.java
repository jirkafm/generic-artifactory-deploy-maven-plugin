package com.github.jirkafm.mvn.auth;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class APIKeyAuthenticationHeaderTest {

	@Test
	public void testApiAuth() {
		final String apiKey = "apiKey";
		AuthenticationHeader authenticationHeader = new APIKeyAuthenticationHeader(apiKey);
		assertEquals("X-JFrog-Art-Api", authenticationHeader.headerKey());
		assertEquals(apiKey, authenticationHeader.headerValue());
	}

}
