package com.github.jirkafm.mvn.deploy;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

import com.github.jirkafm.mvn.StatusTypeToString;

public class ArtifactoryDeployStatusAgregate implements ArtifactoryDeployListener {

	private final Map<File, String> failedDeploys = new HashMap<>();

	@Override
	public void deployRequestComplete(final StatusType statusType, final File file) {
		final StatusTypeToString statusTypeToString = new StatusTypeToString(statusType);
		if (Family.SUCCESSFUL != statusType.getFamily()) {
			failedDeploys.put(file, statusTypeToString.toString());
		}
	}

	public boolean deploySuccesfull() {
		return failedDeploys.isEmpty();
	}

	@Override
	public String toString() {
		if (deploySuccesfull()) {
			return "All files deployed successfully.";
		}

		return generateErrorMessage();
	}

	private String generateErrorMessage() {
		final StringBuilder stringBuilder = new StringBuilder("Unable to process files:");
		stringBuilder.append(System.lineSeparator());
		failedDeploys.forEach((file, errorPhrase) -> {
			stringBuilder.append(errorPhrase);
			stringBuilder.append(' ');
			stringBuilder.append(file.getName());
			stringBuilder.append(System.lineSeparator());
		});
		return stringBuilder.toString().trim();
	}

}
