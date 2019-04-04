package com.github.jirkafm.mvn.checksum;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class ChecksumAlgorithmTest {

	private static final File TEST_FILE = new File("src/test/resources/LICENSE-2.0.zip");
	private static final String CHECKSUM_HEADER = "X-Checksum-";

	@Test
	public void testSha1() {
		final ChecksumAlgorithm testAlgorithm = ChecksumAlgorithm.SHA1;
		assertEquals(CHECKSUM_HEADER + "Sha1", testAlgorithm.getHeaderName());
		assertEquals("8742bc303a4c57da0eb235b2a8b8b755816cc993", testAlgorithm.getChecksum(TEST_FILE));
	}

	@Test
	public void testSha256() {
		final ChecksumAlgorithm testAlgorithm = ChecksumAlgorithm.SHA256;
		assertEquals(CHECKSUM_HEADER + "Sha256", testAlgorithm.getHeaderName());
		assertEquals("d11d62c16b35a0dba126156208bec899580810e12fe48b0a5ed260ea3186c031",
				testAlgorithm.getChecksum(TEST_FILE));
	}

	@Test
	public void testMD5() {
		final ChecksumAlgorithm testAlgorithm = ChecksumAlgorithm.MD5;
		assertEquals(CHECKSUM_HEADER + "MD5", testAlgorithm.getHeaderName());
		assertEquals("e950703edf9befa53c2af66784a71ad9", testAlgorithm.getChecksum(TEST_FILE));
	}

}
