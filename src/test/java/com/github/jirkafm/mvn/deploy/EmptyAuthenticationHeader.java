package com.github.jirkafm.mvn.deploy;

import com.github.jirkafm.mvn.auth.AuthenticationHeader;

public class EmptyAuthenticationHeader implements AuthenticationHeader {

	@Override
	public String headerKey() {
		return "testKey";
	}

	@Override
	public String headerValue() {
		return "testValue";
	}

}
