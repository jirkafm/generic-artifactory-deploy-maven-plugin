package com.github.jirkafm.mvn.deploy;

import java.io.File;

import javax.ws.rs.core.Response.StatusType;

public interface ArtifactoryDeployListener {

	public void deployRequestComplete(final StatusType statusType, final File file);

}
