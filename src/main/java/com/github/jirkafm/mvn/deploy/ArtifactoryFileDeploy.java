package com.github.jirkafm.mvn.deploy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import com.github.jirkafm.mvn.invocationbuilder.BuilderFactory;

public class ArtifactoryFileDeploy {

	private final BuilderFactory builderFactory;
	private final ArtifactoryFileDeployInput input;
	private final List<ArtifactoryDeployListener> listeners = new ArrayList<>();

	public ArtifactoryFileDeploy(final BuilderFactory builderFactory, final ArtifactoryFileDeployInput input) {
		this.builderFactory = builderFactory;
		this.input = input;
	}

	public void deploy() {
		final Entity<File> fileEntity = input.createFileEntity();
		final Invocation.Builder builder = builderFactory.getInstance(fileEntity.getEntity());
		final Response response = builder.method(input.getHttpMethod(), fileEntity);
		final StatusType statusType = response.getStatusInfo();
		listeners.forEach(listener -> listener.deployRequestComplete(statusType, fileEntity.getEntity()));
	}

	public void addListener(final ArtifactoryDeployListener deployListener) {
		this.listeners.add(deployListener);
	}

}
