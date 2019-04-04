package com.github.jirkafm.mvn.checksum;

import java.io.File;

public interface Checksum {

	String getChecksum(final File file);

}
