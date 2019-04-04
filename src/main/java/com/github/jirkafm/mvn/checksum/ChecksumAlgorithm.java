package com.github.jirkafm.mvn.checksum;

import java.io.File;
import java.io.IOException;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

@SuppressWarnings("deprecation")
public enum ChecksumAlgorithm implements Checksum {

	SHA1("Sha1", Hashing.sha1()), SHA256("Sha256", Hashing.sha256()), MD5("MD5", Hashing.md5());

	private String headerName;
	private HashFunction hashFunction;

	private ChecksumAlgorithm(final String headerName, final HashFunction hashFunction) {
		this.headerName = headerName;
		this.hashFunction = hashFunction;
	}

	@Override
	public String getChecksum(final File file) {
		try {
			final HashCode hashCode = Files.asByteSource(file).hash(hashFunction);
			return hashCode.toString();
		} catch (final IOException e) {
			throw new ChecksumException(e);
		}
	}

	public String getHeaderName() {
		return "X-Checksum-" + headerName;
	}

}
