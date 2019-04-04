package com.github.jirkafm.mvn.auth;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TokenAuthenticationHeaderTest {

	@Test
	public void testTokenAuth() {
		final String expectedAuthHeaderData = "Bearer token";
		AuthenticationHeader authenticationHeader = new TokenAuthenticatonHeader("token");
		assertEquals("Authorization", authenticationHeader.headerKey());
		assertEquals(expectedAuthHeaderData, authenticationHeader.headerValue());
	}

}
