package com.github.jirkafm.mvn.deploy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;

import com.github.jirkafm.mvn.checksum.ChecksumAlgorithm;

public class ArtifactoryHeadersTest {

	private static final File TEST_FILE = new File("src/test/resources/LICENSE-2.0.zip");

	@Test
	public void testGetHeaders() {
		final ArtifactoryHeaders artifactoryHeaders = new ArtifactoryHeaders(null, null,
				new EmptyAuthenticationHeader());

		final Map<String, String> headers = artifactoryHeaders.getHeaders(null);
		assertEquals("expected only auth header", 1, headers.size());
		assertThat(headers, IsMapContaining.hasEntry("testKey", "testValue"));
	}

	@Test
	public void testGetHeadersWithChecksum() {
		final List<ChecksumAlgorithm> algorithms = Collections.singletonList(ChecksumAlgorithm.MD5);
		final ArtifactoryHeaders artifactoryHeaders = new ArtifactoryHeaders(null, algorithms,
				new EmptyAuthenticationHeader());

		final Map<String, String> headers = artifactoryHeaders.getHeaders(TEST_FILE);
		assertEquals("auth, checksum", 2, headers.size());
		assertThat(headers, IsMapContaining.hasEntry("testKey", "testValue"));
		assertThat(headers, IsMapContaining.hasEntry("X-Checksum-MD5", "e950703edf9befa53c2af66784a71ad9"));
	}

	@Test
	public void testGetHeadersUserDefinedHeaders() {
		final String userHeader = "userHeader";
		final String userHeaderValue = "userHeaderValue";
		final Map<String, String> userDefined = Collections.singletonMap(userHeader, userHeaderValue);
		final ArtifactoryHeaders artifactoryHeaders = new ArtifactoryHeaders(userDefined, null,
				new EmptyAuthenticationHeader());

		final Map<String, String> headers = artifactoryHeaders.getHeaders(null);
		assertEquals("auth, userDefinedheader", 2, headers.size());
		assertThat(headers, IsMapContaining.hasEntry("testKey", "testValue"));
		assertThat(headers, IsMapContaining.hasEntry(userHeader, userHeaderValue));
	}

}
