package com.github.jirkafm.mvn;

import java.io.File;

import javax.ws.rs.core.Response.StatusType;

import org.slf4j.LoggerFactory;

import com.github.jirkafm.mvn.deploy.ArtifactoryDeployListener;

public class DeployStatusListener implements ArtifactoryDeployListener {

	private final RemoteFileLocation remoteFileLocation;

	public DeployStatusListener(final RemoteFileLocation remoteFileLocation) {
		this.remoteFileLocation = remoteFileLocation;
	}

	@Override
	public void deployRequestComplete(final StatusType statusType, final File file) {
		final String fileName = file.getName();
		final String location = remoteFileLocation.getLocation();
		final StatusTypeToString statusTypeToString = new StatusTypeToString(statusType);
		LoggerFactory.getLogger(getClass()).info("{} {} - {}", statusTypeToString, fileName, location);
	}

}
