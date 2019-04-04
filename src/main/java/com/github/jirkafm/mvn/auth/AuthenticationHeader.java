package com.github.jirkafm.mvn.auth;

public interface AuthenticationHeader {

	/**
	 * @return auth header key
	 */
	public String headerKey();

	/**
	 * @return auth header value
	 */
	public String headerValue();

}
