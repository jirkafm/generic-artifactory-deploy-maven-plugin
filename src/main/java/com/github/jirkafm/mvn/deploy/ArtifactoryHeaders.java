package com.github.jirkafm.mvn.deploy;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.github.jirkafm.mvn.auth.AuthenticationHeader;
import com.github.jirkafm.mvn.checksum.ChecksumAlgorithm;

public class ArtifactoryHeaders {

	private final Map<String, String> headers;
	private final List<ChecksumAlgorithm> fileChecksums;
	private final AuthenticationHeader authHeader;

	public ArtifactoryHeaders(final Map<String, String> headers, final List<ChecksumAlgorithm> fileChecksums,
			final AuthenticationHeader authHeader) {
		this.headers = Optional.ofNullable(headers).orElse(Collections.emptyMap());
		this.fileChecksums = Optional.ofNullable(fileChecksums).orElse(Collections.emptyList());
		this.authHeader = Objects.requireNonNull(authHeader);
	}

	public Map<String, String> getHeaders(final File file) {
		final Map<String, String> artifactoryHeaders = new HashMap<>(headers);

		for (final ChecksumAlgorithm checksum : fileChecksums) {
			artifactoryHeaders.put(checksum.getHeaderName(), checksum.getChecksum(file));
		}

		artifactoryHeaders.put(authHeader.headerKey(), authHeader.headerValue());
		return artifactoryHeaders;
	}

}
