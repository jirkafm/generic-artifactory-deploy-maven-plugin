package com.github.jirkafm.mvn.checksum;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class ChecksumAlgorithmTest {

	private static final File TEST_FILE = new File("src/test/resources/LICENSE-2.0.txt");
	private static final String CHECKSUM_HEADER = "X-Checksum-";

	@Test
	public void testSha1() {
		ChecksumAlgorithm testAlgorithm = ChecksumAlgorithm.SHA1;
		assertEquals(CHECKSUM_HEADER + "Sha1", testAlgorithm.getHeaderName());
		assertEquals("2b8b815229aa8a61e483fb4ba0588b8b6c491890", testAlgorithm.getChecksum(TEST_FILE));
	}

	@Test
	public void testSha256() {
		ChecksumAlgorithm testAlgorithm = ChecksumAlgorithm.SHA256;
		assertEquals(CHECKSUM_HEADER + "Sha256", testAlgorithm.getHeaderName());
		assertEquals("cfc7749b96f63bd31c3c42b5c471bf756814053e847c10f3eb003417bc523d30",
				testAlgorithm.getChecksum(TEST_FILE));
	}

	@Test
	public void testMD5() {
		ChecksumAlgorithm testAlgorithm = ChecksumAlgorithm.MD5;
		assertEquals(CHECKSUM_HEADER + "MD5", testAlgorithm.getHeaderName());
		assertEquals("3b83ef96387f14655fc854ddc3c6bd57", testAlgorithm.getChecksum(TEST_FILE));
	}

}
