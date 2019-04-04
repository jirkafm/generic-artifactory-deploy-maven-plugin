package com.github.jirkafm.mvn.invocationbuilder;

import java.io.File;

import javax.ws.rs.client.Invocation.Builder;

public interface BuilderFactory {

	public Builder getInstance(final File file);

}
