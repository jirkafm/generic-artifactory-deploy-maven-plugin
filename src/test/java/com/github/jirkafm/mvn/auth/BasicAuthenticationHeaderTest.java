package com.github.jirkafm.mvn.auth;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BasicAuthenticationHeaderTest {

	@Test
	public void testBasicAuth() {
		final String expectedAuthHeaderData = "Basic Sm9obiBEb2U6cGFzc3dvcmQ=";
		AuthenticationHeader authenticationHeader = new BasicAuthenticationHeader("John Doe", "password");
		assertEquals("Authorization", authenticationHeader.headerKey());
		assertEquals(expectedAuthHeaderData, authenticationHeader.headerValue());
	}

}
