package com.github.jirkafm.mvn.deploy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

import org.junit.Test;

public class ArtifactoryDeployStatusAgregateTest {

	private static final StatusType ERROR_STATUS_TYPE = new StatusType() {

		@Override
		public int getStatusCode() {
			return 500;
		}

		@Override
		public String getReasonPhrase() {
			return "Server error";
		}

		@Override
		public Family getFamily() {
			return Family.SERVER_ERROR;
		}
	};

	@Test
	public void testDeploySucessfull() {
		final ArtifactoryDeployStatusAgregate agregate = new ArtifactoryDeployStatusAgregate();
		assertTrue(agregate.deploySuccesfull());

		agregate.deployRequestComplete(ERROR_STATUS_TYPE, new File("."));
		assertFalse(agregate.deploySuccesfull());
	}

	@Test
	public void testToString() {
		final ArtifactoryDeployStatusAgregate agregate = new ArtifactoryDeployStatusAgregate();
		assertTrue(agregate.toString().contains("success"));

		agregate.deployRequestComplete(ERROR_STATUS_TYPE, new File("."));
		assertTrue(agregate.toString().contains("[500] Server error ."));
	}

}
