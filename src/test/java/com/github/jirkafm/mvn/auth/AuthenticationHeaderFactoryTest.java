package com.github.jirkafm.mvn.auth;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AuthenticationHeaderFactoryTest {

	@Test
	public void testUsernamePassowrd() {
		AuthenticationHeaderFactory factory = new AuthenticationHeaderFactory("John Doe", "password", null, null);
		assertTrue(factory.getInstance() instanceof BasicAuthenticationHeader);
	}

	@Test
	public void testApiKey() {
		AuthenticationHeaderFactory factory = new AuthenticationHeaderFactory(null, null, null, "apiKey");
		assertTrue(factory.getInstance() instanceof APIKeyAuthenticationHeader);
	}

	@Test
	public void testAccesToken() {
		AuthenticationHeaderFactory factory = new AuthenticationHeaderFactory(null, null, "accessToken", null);
		assertTrue(factory.getInstance() instanceof TokenAuthenticatonHeader);
	}

	@Test(expected = AuthenticatonHeaderCreationException.class)
	public void testInvalidConfigurationUsernameOnly() {
		AuthenticationHeaderFactory factory = new AuthenticationHeaderFactory("John Doe", null, null, null);
		factory.getInstance();
	}

	@Test(expected = AuthenticatonHeaderCreationException.class)
	public void testInvalidConfigurationPasswordOnly() {
		AuthenticationHeaderFactory factory = new AuthenticationHeaderFactory(null, "password", null, null);
		factory.getInstance();
	}

	@Test(expected = AuthenticatonHeaderCreationException.class)
	public void testInvalidConfiguration() {
		AuthenticationHeaderFactory factory = new AuthenticationHeaderFactory(null, null, null, null);
		factory.getInstance();
	}

}
